package life.majiang.community.service;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sam
 * @create 2024-06-07 7:32 PM
 */

@Service
public class CommentService {

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private QuestionMapper questionMapper;

  @Autowired
  private QuestionExtMapper questionExtMapper;

  @Autowired
  private UserMapper userMapper;

  /** @Transactional注解使下面方法包裹在一个事物里面，如果commentMapper.insert执行成功，而questionExtMapper.incCommentCount
   * 执行失败时，事物会全部回滚掉 */
  @Transactional
  public void insert(Comment comment) throws CustomizeException {
    if(comment.getParentId() == null) {
      throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
    }
    if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
      throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
    }
    if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
      //回复评论
      Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
      if (dbComment == null){
        throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
      }
      commentMapper.insert(comment);
    } else {
      //回复问题
      Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
      if (question == null) {
        throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
      }
      commentMapper.insert(comment);
      question.setCommentCount(1);
      questionExtMapper.incCommentCount(question);
    }
  }

  public List<CommentDTO> listByQuestionId(Long id) {
    CommentExample commentExample = new CommentExample();
    commentExample.createCriteria()
            .andParentIdEqualTo(id)
            .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
    commentExample.setOrderByClause("gmt_create ASC");
    List<Comment> comments = commentMapper.selectByExample(commentExample);

    if(comments.size() == 0) {
      return new ArrayList<>();
    }
    // 获取去重的评论人
    Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
    List<Long> userIds = new ArrayList<>();
    userIds.addAll(commentators);

    // 获取评论人并转换为Map
    UserExample userExample = new UserExample();
    userExample.createCriteria()
            .andIdIn(userIds);
    List<User> users = userMapper.selectByExample(userExample);
    Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

    // 转换 comment 为 commentDTO
    List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
      CommentDTO commentDTO = new CommentDTO();
      BeanUtils.copyProperties(comment, commentDTO);
      commentDTO.setUser(userMap.get(comment.getCommentator()));
      return commentDTO;
    }).collect(Collectors.toList());

    return commentDTOS;
  }
}

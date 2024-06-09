package life.majiang.community.service;

import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

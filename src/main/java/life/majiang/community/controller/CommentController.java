package life.majiang.community.controller;

import life.majiang.community.dto.CommentCreateDTO;
import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Sam
 * @create 2024-06-06 4:07 PM
 */

@Controller
public class CommentController {

  @Autowired
  private CommentService commentService;

  @ResponseBody
  @RequestMapping(value = "/comment", method = RequestMethod.POST)
  /** @RequestBody注释可以自动解析Oject里面的fields */
  public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                     HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");
    if(user == null){
      return ResultDTO.errofOf(CustomizeErrorCode.NO_LOGIN);
    }
    if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
      return ResultDTO.errofOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
    }



    Comment comment = new Comment();
    comment.setParentId(commentCreateDTO.getParentId());
    comment.setContent(commentCreateDTO.getContent());
    comment.setType(commentCreateDTO.getType());
    comment.setGmtCreate(System.currentTimeMillis());
    comment.setGmtModified(System.currentTimeMillis());
    comment.setCommentator(user.getId());
    comment.setLikeCount(0L);
    commentService.insert(comment, user);
    return ResultDTO.okOf();
  }

  @ResponseBody
  @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
  public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
    List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
    return ResultDTO.okOf(commentDTOS);
  }



}
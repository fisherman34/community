package life.majiang.community.controller;

import life.majiang.community.dto.CommentCreateDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    Comment comment = new Comment();
    comment.setParentId(commentCreateDTO.getParentId());
    comment.setContent(commentCreateDTO.getContent());
    comment.setType(commentCreateDTO.getType());
    comment.setGmtCreate(System.currentTimeMillis());
    comment.setGmtModified(System.currentTimeMillis());
    comment.setCommentator(user.getId());
    comment.setLikeCount(0L);
    commentService.insert(comment);
    return ResultDTO.okOf();
  }
}
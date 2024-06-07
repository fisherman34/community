package life.majiang.community.controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.mapper.CommentMapper;
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
import java.util.HashMap;
import java.util.Map;

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
  public Object post(@RequestBody CommentDTO commentDTO,
                     HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute("user");
    if(user == null){
      return ResultDTO.errofOf(CustomizeErrorCode.NO_LOGIN);
    }
    Comment comment = new Comment();
    comment.setParentId(commentDTO.getParentId());
    comment.setContent(commentDTO.getContent());
    comment.setType(commentDTO.getType());
    comment.setGmtCreate(System.currentTimeMillis());
    comment.setGmtModified(System.currentTimeMillis());
    comment.setCommentator(user.getId());
    comment.setLikeCount(0L);
    commentService.insert(comment);
    return ResultDTO.okOf();
  }
}
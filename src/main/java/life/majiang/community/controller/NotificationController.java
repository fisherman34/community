package life.majiang.community.controller;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam
 * @create 2024-07-05 4:49 PM
 */

@Controller
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @GetMapping("/notification/{id}")
  public String profile(HttpServletRequest request,
                        @PathVariable(name = "id") Long id) {


    User user = (User) request.getSession().getAttribute("user");
    if(user ==null){
      return "redirect:/";  //如果user为空，则返回首页
    }

    NotificationDTO notificationDTO = notificationService.read(id, user);

    if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
     || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
      return "redirect:/question/" + notificationDTO.getOuterid();
    } else {
      return "redirect:/";
    }
  }
}

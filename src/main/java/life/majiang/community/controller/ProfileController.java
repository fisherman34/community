package life.majiang.community.controller;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationService;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam
 * @create 2024-06-03 10:23 AM
 */

@Controller
public class ProfileController {

  @Autowired
  private QuestionService questionService;

  @Autowired
  private NotificationService notificationService;

  /** /profile/{action}的写法可以动态解析URL地址， @PathVariable(name = "action")的注解
   * 能够吧URL里的参数对应到函数里的参数
   * */
  @GetMapping("/profile/{action}")
  public String profile(HttpServletRequest request,
                        @PathVariable(name = "action") String action,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {

    /** 请求cookie,是用request, getCookies()来获取cookie */

    User user = (User) request.getSession().getAttribute("user");
    if(user ==null){
      return "redirect:/";  //如果user为空，则返回首页
    }


    if("questions".equals(action)) {  //如果访问的链接为/profile/questions，则..
      model.addAttribute("section", "questions");
      model.addAttribute("sectionName", "私の投稿");
      PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
      model.addAttribute("pagination", paginationDTO);
    } else if ("replies".equals(action)) {
      PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
      model.addAttribute("section", "replies");
      model.addAttribute("pagination", paginationDTO);
      model.addAttribute("sectionName", "最新の返信");
    }

    return "profile";
  }
}

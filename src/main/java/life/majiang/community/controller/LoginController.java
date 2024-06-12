package life.majiang.community.controller;

import life.majiang.community.enums.GeneralConst;
import life.majiang.community.model.User;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Sam
 * @create 2024-06-11 6:32 PM
 */

@Controller
public class LoginController {

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public String signup(HttpServletRequest request){

    User user = (User) request.getSession().getAttribute("user");
    if(user != null ){
      return "redirect:/";
    }

    return "login";
  }

  @PostMapping("/login")
  public String dosignup(
          @RequestParam(value = "username", required = false) String username,
          @RequestParam(value ="password", required = false) String password,
          HttpServletResponse response,
          Model model) {

    model.addAttribute("username", username);

    if (username == null || username == "") {
      model.addAttribute("error", GeneralConst.USERNAME_EMPTY);
      return "login";
    }
    if (password == null || password == "") {
      model.addAttribute("error", GeneralConst.PASSWORD_EMPTY);
      return "login";
    }

    User user = userService.getByName(username);  //输入用户名去数据库中查找

    if (user == null) {  //若用户为空
      model.addAttribute("error", GeneralConst.USER_NOT_EXIST);
      return "login";
    }

    if (username.equals(user.getName()) && password.equals(user.getPassword())) {  //帐号密码正确
      String token = UUID.randomUUID().toString();
      user.setToken(token);
      userService.createOrUpdate(user);

      response.addCookie(new Cookie("token", token));
      return "redirect:/";

    } else if (!password.equals(user.getPassword())) {
      model.addAttribute("error", GeneralConst.PASSWORD_NOT_CORRECT);
      return "login";
    }
    return "login";
  }
}

package life.majiang.community.controller;

import life.majiang.community.enums.GeneralConst;
import life.majiang.community.model.User;
import life.majiang.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
public class SignupController {

  @Autowired
  private UserService userService;

  @GetMapping("/signup")
  public String signup(HttpServletRequest request){

    User user = (User) request.getSession().getAttribute("user");
    if(user != null ){
      return "redirect:/";
    }

    return "signup";
  }

  @PostMapping("/signup")
  public String dosignup(
          @RequestParam(value = "username", required = false) String username,
          @RequestParam(value ="password1", required = false) String password1,
          @RequestParam(value ="password2", required = false) String password2,
          HttpServletResponse response,
          Model model) {

    model.addAttribute("username", username);

    if (username == null || username == "") {
      model.addAttribute("error", GeneralConst.USERNAME_EMPTY);
      return "signup";
    }
    if (password1 == null || password1 == "") {
      model.addAttribute("error", GeneralConst.PASSWORD_EMPTY);
      return "signup";
    }
    if (password2 == null || password2 == "") {
      model.addAttribute("error", GeneralConst.PASSWORD_EMPTY);
      return "signup";
    }
    if (!password1.equals(password2)) {
      model.addAttribute("error", GeneralConst.PASSWORD_NOT_MATCH);
      return "signup";
    }
    if (StringUtils.containsWhitespace(username)) {
      model.addAttribute("error", GeneralConst.USERNAME_CONTAINS_WHITESPACE);
      return "signup";
    }
    if (StringUtils.containsWhitespace(password1)) {
      model.addAttribute("error", GeneralConst.PASSWORD_CONTAINS_WHITESPACE);
      return "signup";
    }
    if (StringUtils.containsWhitespace(password2)) {
      model.addAttribute("error", GeneralConst.PASSWORD_CONTAINS_WHITESPACE);
      return "signup";
    }
    if (username.length() < 3 || username.length() > 20) {
      model.addAttribute("error", GeneralConst.USERNAME_LENGTH_NOT_CORRECT);
      return "signup";
    }
    if (password1.length() < 6 || password1.length() > 20) {
      model.addAttribute("error", GeneralConst.PASSWORD_LENGTH_NOT_CORRECT);
      return "signup";
    }

    User user = userService.getByName(username);  //输入用户名去数据库中查找
    if (user != null) {  //若用户名已经存在
      model.addAttribute("error", GeneralConst.USERNAME_ALREADY_EXIST);
      return "signup";
    }

    Long maxId = userService.getMaxId();
    Long accountIdPlus = userService.accountIdPlus(maxId);

    User newUser = new User();
    String token = UUID.randomUUID().toString();
    newUser.setToken(token);
    newUser.setName(username);
    newUser.setAccountId(String.valueOf(accountIdPlus));
    newUser.setPassword(password1);
    newUser.setAvatarUrl("/img/avatar.jpg");
    userService.createOrUpdate(newUser);

    response.addCookie(new Cookie("token", token));
    return "redirect:/";

  }
}

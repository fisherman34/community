package life.majiang.community.controller;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam
 * @create 2024-04-29 2:51 PM
 */
@Controller
public class IndexController {

  @Autowired
  private UserMapper userMapper;
  @GetMapping("/")
  public String index(HttpServletRequest request){
    /** 请求cookie,是用request.getCookies()来获取cookie */
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("token")){
        String token = cookie.getValue();
        User user = userMapper.findByToken(token);
        if (user != null) {
          request.getSession().setAttribute("user", user);
        }
        break;
      }
    }
    return "index";
  }
}


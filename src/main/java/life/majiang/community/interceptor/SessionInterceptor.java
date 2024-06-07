package life.majiang.community.interceptor;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设置 interceptors,对所有地址进行拦截，请求地址的时候对統一對所有页面
 * 按以下代码查找token
 * @author Sam
 * @create 2024-06-03 5:22 PM
 */

//@Component
@Service
public class SessionInterceptor implements HandlerInterceptor {

  @Autowired
  private UserMapper userMapper;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    /**
     * What interceptor do:
     * 1.Retrieve the token from the cookie in the incoming request.
     * 2.Use this token to look up the User object from the database.
     * 3.Store the User object in the session: request.getSession().setAttribute("user", user);.
     * Then, other controller methods, can retrieve the User object from the session
     *
     * 请求cookie,是用request.getCookies()来获取cookie */
    Cookie[] cookies = request.getCookies();
    if(cookies !=null && cookies.length !=0) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("token")){
          String token = cookie.getValue();
          UserExample userExample = new UserExample();
          userExample.createCriteria()  //拼接各种SQL
                  .andTokenEqualTo(token);
          List<User> users = userMapper.selectByExample(userExample);
          /** request.getSession() : retrieves the current HttpSession associated with
           * this request or, if there is no current session and one is needed, creates
           * a new session.
           * setAttribute("user", user) adds an attribute to the session under the name “user”.
           * This attribute is then available to the server during the entire session and can
           * be retrieved in subsequent requests.
           * */
          if (users.size() != 0) {
            request.getSession().setAttribute("user", users.get(0));
          }
          break;
        }
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}

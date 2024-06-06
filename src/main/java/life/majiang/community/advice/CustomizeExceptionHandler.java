package life.majiang.community.advice;

import life.majiang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam
 * @create 2024-06-05 7:46 PM
 */

@ControllerAdvice
public class CustomizeExceptionHandler {

  @ExceptionHandler(Exception.class)
  ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
    if(e instanceof CustomizeException){
      model.addAttribute("message", e.getMessage());
    } else {
      model.addAttribute("message", "服务冒烟了，要不你稍后再试试！！！");
    }
    return new ModelAndView("error");
  }
}

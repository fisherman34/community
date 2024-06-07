package life.majiang.community.advice;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Sam
 * @create 2024-06-05 7:46 PM
 */

@ControllerAdvice
public class CustomizeExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(CustomizeExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {

    String contentType = request.getContentType();
    ResultDTO resultDTO;

    if ("application/json".equals(contentType)) {
      // Log the exception details to the terminal
      logger.error("Error: ", e);
      //返回 JSON
      if(e instanceof CustomizeException){
        resultDTO = ResultDTO.errofOf((CustomizeException)e);
      } else {
        resultDTO = ResultDTO.errofOf(CustomizeErrorCode.SYS_ERROR);
      }
      try {
        response.setContentType("application/json");
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(resultDTO));
        writer.close();
      } catch (IOException ioe) {
      }
      return null;
    } else{
      // 进行页面跳转
      if(e instanceof CustomizeException){
        model.addAttribute("message", e.getMessage());
      } else {
        model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
      }
      // Log the exception details to the terminal
      logger.error("Error: ", e);

      return new ModelAndView("error");
    }

  }
}

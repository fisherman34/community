package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Sam
 * @create 2024-04-29 2:51 PM
 */
@Controller
public class HelloController {

  @GetMapping("/hello")
  public String hello(@RequestParam(name = "name") String name, Model model){
    model.addAttribute("name", name);
    return "hello";
  }
}


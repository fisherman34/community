package life.majiang.community.controller;

import life.majiang.community.cache.TagCache;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam
 * @create 2024-05-29 6:32 PM
 */

@Controller
public class PublishController {

  @Autowired
  private QuestionService questionService;

  /** 修改页面 */
  @GetMapping("/publish/{id}")
  public String edit(@PathVariable(name = "id") Long id,
                     Model model) {
    QuestionDTO question = questionService.getById(id);
    model.addAttribute("title", question.getTitle());
    model.addAttribute("description", question.getDescription());
    model.addAttribute("tag", question.getTag());
    model.addAttribute("id", question.getId());
    model.addAttribute("tags", TagCache.get());
    return "publish";
  }

  /** 如果是get method的话就渲染页面 */
  @GetMapping("/publish")
  public String publish(Model model) {
    model.addAttribute("tags", TagCache.get());
    return "publish";
  }

  /** 如果是post method的话就.... */
  @PostMapping("/publish")
  public String doPublish(
          /** @RequestParam annotation is specifying that a parameter of
           * the HTTP request should be bound to a method parameter.
           * When the doPublish method is called, Spring will take the HTTP
           * request parameters named title, description, and tag and pass them to the
           * method as the title, description, and tag parameters, respectively.
           *
           * The required = false attribute indicates that this request parameter is
           * not mandatory for the request to be processed.
           * */
          @RequestParam(value = "title", required = false) String title,
          @RequestParam(value ="description", required = false) String description,
          @RequestParam(value ="tag", required = false) String tag,
          @RequestParam(value = "id", required = false) Long id,
          HttpServletRequest request,
          Model model) {

    model.addAttribute("title", title);
    model.addAttribute("description", description);
    model.addAttribute("tag", tag);
    model.addAttribute("tags", TagCache.get());

    //把换行符替换成<br>符号，因为换行符在HTML中会被解析成空格，无法正常显示
//    description = description.replace("\n", "<br>");

    if(title == null || title == "") {
      model.addAttribute("error", "タイトルは空欄にできません");
      return "publish";
    }
    if(description == null || description == "") {
      model.addAttribute("error", "本文は空欄にできません");
      return "publish";
    }
    if(tag == null || tag == "") {
      model.addAttribute("error", "キーワードは空欄にできません");
      return "publish";
    }

    String invalid = TagCache.filterInvalid(tag);
    if(StringUtils.isNotBlank(invalid)){
      model.addAttribute("error", "間違ったタグを入力しました:" + invalid);
      return "publish";
    }

    User user = (User) request.getSession().getAttribute("user");
    if(user == null ){
      model.addAttribute("error", "ユーザーがログインしていません");
      return "publish";
    }

    Question question = new Question();
    question.setTitle(title);
    question.setDescription(description);
    question.setTag(tag);
    question.setCreator(user.getId());
    question.setId(id);
    questionService.createOrUpdate(question);
    return "redirect:/";
  }
}

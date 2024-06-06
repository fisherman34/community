package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

/**
 * DTOs are typically implemented as simple Java classes with attributes and
 * accessors but no business logic.
 * @author Sam
 * @create 2024-05-30 6:41 PM
 */
@Data
public class QuestionDTO {
  private Integer id;
  private String title;
  private String description;
  private String tag;
  private Long gmtCreate;
  private Long gmtModified;
  private Integer creator;
  private Integer viewCount;
  private Integer commentCount;
  private Integer likeCount;
  private User user;
}

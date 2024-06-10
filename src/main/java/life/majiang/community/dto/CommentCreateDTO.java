package life.majiang.community.dto;

import lombok.Data;

/**
 * @author Sam
 * @create 2024-06-06 4:45 PM
 */

@Data
public class CommentCreateDTO {
  private Long parentId;
  private String content;
  private Integer type;
}

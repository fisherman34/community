package life.majiang.community.dto;

import lombok.Data;

/**
 * @author Sam
 * @create 2024-07-11 11:04 AM
 */

@Data
public class QuestionQueryDTO {
  private String search;
  private Integer page;
  private Integer size;
}

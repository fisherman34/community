package life.majiang.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Sam
 * @create 2024-07-03 5:29 PM
 */

@Data
public class TagDTO {
  private String categoryName;
  private List<String> tags;
}

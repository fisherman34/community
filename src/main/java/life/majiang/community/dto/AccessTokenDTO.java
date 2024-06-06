package life.majiang.community.dto;

import lombok.Data;

/**
 * @author Sam
 * @create 2024-05-02 10:59 PM
 */
@Data
public class AccessTokenDTO {
  private String client_id;
  private String client_secret;
  private String code;
  private String redirect_uri;
}

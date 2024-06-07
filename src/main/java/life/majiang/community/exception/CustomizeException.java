package life.majiang.community.exception;

/**
 * @author Sam
 * @create 2024-06-05 8:15 PM
 */
public class CustomizeException extends RuntimeException {
  private String message;
  private Integer code;

  public CustomizeException(ICustomizeErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Integer getCode() {
    return code;
  }
}

package life.majiang.community.exception;

/**
 * @author Sam
 * @create 2024-06-05 8:15 PM
 */
public class CustomizeException extends RuntimeException {
  private String message;

  public CustomizeException(String message) {
    this.message = message;
  }

  public CustomizeException(ICustomizeErrorCode errorCode) {
    this.message = errorCode.getMessage();
  }

  @Override
  public String getMessage() {
    return message;
  }
}

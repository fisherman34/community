package life.majiang.community.exception;

/**
 * @author Sam
 * @create 2024-06-05 8:48 PM
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

  QUESTION_NOT_FOUND("你找的问题不在了，要不要换个试试");

  private String message;

  CustomizeErrorCode(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}

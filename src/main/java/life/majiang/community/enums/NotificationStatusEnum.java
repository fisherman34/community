package life.majiang.community.enums;

/**
 * @author Sam
 * @create 2024-07-04 6:55 PM
 */
public enum NotificationStatusEnum {
  UNREAD(0),READ(1);

  private int status;

  NotificationStatusEnum(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}

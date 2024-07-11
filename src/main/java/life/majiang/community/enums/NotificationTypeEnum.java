package life.majiang.community.enums;

/**
 * @author Sam
 * @create 2024-07-04 6:26 PM
 */
public enum NotificationTypeEnum {
  REPLY_QUESTION(1, "質問を答えました"),
  REPLY_COMMENT(2,"コメントを答えました");
  private int type;
  private String name;

  NotificationTypeEnum(int status, String name) {
    this.type = status;
    this.name = name;
  }

  public int getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public static String nameOfType(int type) {
    for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
      if (notificationTypeEnum.getType() == type) {
        return notificationTypeEnum.getName();
      }
    }
    return "";
  }
}

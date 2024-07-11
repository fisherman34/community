package life.majiang.community.dto;

import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import lombok.Data;

/**
 * @author Sam
 * @create 2024-06-07 5:18 PM
 */

@Data
public class ResultDTO<T> {
  private Integer code;
  private String message;
  private T data;

  public static ResultDTO errofOf(Integer code, String message){
    ResultDTO resultDTO = new ResultDTO();
    resultDTO.setCode(code);
    resultDTO.setMessage(message);
    return resultDTO;
  }

  public static ResultDTO errofOf(CustomizeErrorCode errorCode) {
    return errofOf(errorCode.getCode(), errorCode.getMessage());
  }

  public static ResultDTO errofOf(CustomizeException e) {
    return errofOf(e.getCode(), e.getMessage());
  }

  public static ResultDTO okOf() {
    ResultDTO resultDTO = new ResultDTO();
    resultDTO.setCode(200);
    resultDTO.setMessage("请求成功");
    return resultDTO;
  }

  public static <T> ResultDTO okOf(T t) {
    ResultDTO resultDTO = new ResultDTO();
    resultDTO.setCode(200);
    resultDTO.setMessage("请求成功");
    resultDTO.setData(t);
    return resultDTO;
  }


}

package life.majiang.community.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sam
 * @create 2024-07-10 5:23 PM
 */
public final class MyUtils {
  private MyUtils() {
    // 私有构造函数，防止实例化
  }


  public static byte[] toByteArray(InputStream stream) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    int nRead;
    byte[] data = new byte[1024];
    while ((nRead = stream.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }
    buffer.flush();
    byte[] result = buffer.toByteArray();
    buffer.close();

    return result;
  }

}



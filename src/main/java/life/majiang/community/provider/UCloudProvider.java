package life.majiang.community.provider;

import life.majiang.community.Utils.MyUtils;
import life.majiang.community.mapper.ImagesMapper;
import life.majiang.community.model.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Sam
 * @create 2024-07-10 4:24 PM
 */

@Service
public class UCloudProvider {

  @Autowired
  private ImagesMapper imagesMapper;

  public String upload(InputStream fileStream, String mimeType, String fileName){

    String generatedFileName;
    String[] filePaths = fileName.split("\\.");
    if (filePaths.length > 1) {
      generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
    } else {
      return null;
    }

    try {
      byte[] data = MyUtils.toByteArray(fileStream);
      fileStream.close();
      Images image = new Images();
      image.setData(data);
      image.setName(generatedFileName);
      imagesMapper.insert(image);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return generatedFileName;
  }
}

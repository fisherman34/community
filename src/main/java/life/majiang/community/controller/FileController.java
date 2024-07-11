package life.majiang.community.controller;

import life.majiang.community.dto.FileDTO;
import life.majiang.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Sam
 * @create 2024-07-08 4:23 PM
 */
public class FileController {

  @Autowired
  private UCloudProvider uCloudProvider;

  @RequestMapping("/file/upload")
  @ResponseBody
  //the upload() method returns a FileDTO object, which will be converted to JSON and sent back as the response.
  public FileDTO upload(HttpServletRequest request){
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile file = multipartRequest.getFile("editormd-image-file");

    try {
      uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
    } catch (IOException e) {
      e.printStackTrace();
    }

    FileDTO fileDTO = new FileDTO();
    fileDTO.setSucess(1);
    fileDTO.setUrl("/images/wechat.jpg");
    return fileDTO;
  }
}

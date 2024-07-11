package life.majiang.community.controller;

import life.majiang.community.mapper.ImagesMapper;
import life.majiang.community.model.Images;
import life.majiang.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Sam
 * @create 2024-07-10 9:48 AM
 */

@Controller
public class FileUploadController {

  @Autowired
  private ImagesMapper imagesMapper;

  /** 如果是get method的话就渲染页面 */
  @GetMapping("/upload")
  public String publish(Model model) {
    return "upload";
  }

  /** 如果是post method的话就上传图片 */
  @PostMapping("/upload")
  public void handleFileUpload(@RequestParam("file") MultipartFile file,
                               String fileName,
                               HttpServletRequest request) throws IOException {

    String generateFileName = "";
    String[] filePaths = fileName.split(".");
    if(filePaths.length > 1) {
      generateFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length-1];
//    } else {
//      return null;
    }


    byte[] data = file.getBytes();
    Images image = new Images();
    image.setData(data);
    image.setName(generateFileName);
    imagesMapper.insert(image);
  }


  /** 显示图片 */
  @GetMapping("/img/{id}")
  public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
    Images image = imagesMapper.selectByPrimaryKey(id);
    byte[] data = image.getData();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);

    return new ResponseEntity<>(data, headers, HttpStatus.OK);
  }
}

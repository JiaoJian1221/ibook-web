package me.jiaojian.controller;

import me.jiaojian.domain.Img;
import me.jiaojian.domain.ImgUsage;
import me.jiaojian.repository.ImgUsageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by jiaojian on 2018/1/10.
 */
@Controller
@RequestMapping("/imgs")
public class ImgController {

  @Autowired
  private ImgUsageRepository imgUsageRepository;

  private File dir = new File("/Users/jiaojian/Downloads/imgs");

  @RequestMapping("/{uid}")
  public ResponseEntity<byte[]> getImg(@PathVariable Long uid) throws IOException {
    ImgUsage usage = imgUsageRepository.findOne(uid);
    return new ResponseEntity<>(read(usage), getHeaders(usage.getImg()), HttpStatus.OK);
  }

  private byte[] read(ImgUsage usage) throws IOException {
    File file = new File(new File(dir, usage.getType().name().toLowerCase()), usage.getImg().getName());
    return IOUtils.toByteArray(new FileInputStream(file));
  }

  private HttpHeaders getHeaders(Img img) {
    HttpHeaders headers = new HttpHeaders();
    if(img.getName().toLowerCase().endsWith(".jpg")) {
      headers.setContentType(MediaType.IMAGE_JPEG);
    }
    if(img.getName().toLowerCase().endsWith(".png")) {
      headers.setContentType(MediaType.IMAGE_PNG);
    }
    if(img.getName().toLowerCase().endsWith(".gif")) {
      headers.setContentType(MediaType.IMAGE_GIF);
    }
    return headers;
  }

}

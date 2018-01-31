package me.jiaojian.domain.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by jiaojian on 2018/1/12.
 */
public interface BookInfo {

  Long getId();
  String getIsbn();
  String getName();
  String getAuthor();
  String getPublisher();
  String getPublishDate();

  @Value("#{target.getImg()}")
  ImgUsageInfo getImg();

  @Value("#{target.getThumbImg()}")
  ImgUsageInfo getThumbImg();
}

package me.jiaojian.domain.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by jiaojian on 2018/1/8.
 */
public interface ImgUsageInfo {
  Long getId();

  @Value("#{target.img.name}")
  String getName();
}

package me.jiaojian.domain.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by jiaojian on 2018/1/8.
 */
public interface ChannelInfo {
  Long getId();

  String getName();

  boolean isSystemCatalog();

  @Value("#{target.usages}")
  List<ImgUsageInfo> getBannerImgs();

  List<CatalogInfo> getCatalogs();

}

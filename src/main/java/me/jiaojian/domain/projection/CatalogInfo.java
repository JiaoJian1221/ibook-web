package me.jiaojian.domain.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by jiaojian on 2018/1/8.
 */
public interface CatalogInfo {

  Long getId();

  String getName();

  //List<BookInfo> getBooks();

  @Value("#{target.channel.id}")
  Long getChannelId();
}

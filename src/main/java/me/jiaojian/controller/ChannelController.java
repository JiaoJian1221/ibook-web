package me.jiaojian.controller;

import me.jiaojian.domain.Channel;
import me.jiaojian.domain.ImgUsage;
import me.jiaojian.domain.projection.CatalogInfo;
import me.jiaojian.domain.projection.ChannelInfo;
import me.jiaojian.domain.projection.ImgUsageInfo;
import me.jiaojian.repository.CatalogRepository;
import me.jiaojian.repository.ChannelRepository;
import me.jiaojian.repository.ImgUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static me.jiaojian.repository.Repository.and;
import static me.jiaojian.repository.Repository.equal;

/**
 * Created by jiaojian on 2017/12/29.
 */
@RestController
@RequestMapping("/channels")
public class ChannelController {

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private CatalogRepository catalogRepository;

  @Autowired
  private ImgUsageRepository imgUsageRepository;

  @GetMapping
  public List<ChannelInfo> getChannels() {
    return channelRepository.findAll(ChannelInfo.class).subList(0, 10);
  }

  @GetMapping("{id}")
  public ChannelInfo getChannel(@PathVariable Long id) {
    return channelRepository.findOne(id, ChannelInfo.class);
  }

  @GetMapping("/{id}/catalogs")
  public List<CatalogInfo> getCatalogs(@PathVariable Long id) {
    Channel channel = channelRepository.findOne(id);
    return catalogRepository.findAll(equal("channel", channel), CatalogInfo.class);
  }

  @GetMapping("/{id}/imgs")
  public List<ImgUsageInfo> getImgUsages(@PathVariable Long id) {
    return imgUsageRepository.findAll(and(equal("objId", id), equal("type", ImgUsage.Type.CHANNEL_BANNER_IMG)), ImgUsageInfo.class);
  }
}

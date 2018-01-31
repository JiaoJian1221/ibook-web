package me.jiaojian.repository;

import me.jiaojian.domain.Channel;

/**
 * Created by jiaojian on 2017/12/29.
 */
public interface ChannelRepository extends Repository<Channel, Long> {
  Channel findByJdUri(String href);
}

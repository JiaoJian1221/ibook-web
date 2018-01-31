package me.jiaojian.repository;

import me.jiaojian.domain.Img;

/**
 * Created by jiaojian on 2017/12/29.
 */
public interface ImgRepository extends Repository<Img, Long> {
  Img findByJdUri(String href);
}

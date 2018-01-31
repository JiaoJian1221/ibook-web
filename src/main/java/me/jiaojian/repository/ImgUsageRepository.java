package me.jiaojian.repository;

import me.jiaojian.domain.Img;
import me.jiaojian.domain.ImgUsage;

/**
 * Created by jiaojian on 2017/12/29.
 */
public interface ImgUsageRepository extends Repository<ImgUsage, Long> {

  ImgUsage findByImgAndTypeAndObjId(Img img, ImgUsage.Type type, Long objId);
}

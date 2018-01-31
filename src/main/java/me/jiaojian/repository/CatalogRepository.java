package me.jiaojian.repository;

import me.jiaojian.domain.Catalog;

/**
 * Created by jiaojian on 2017/12/29.
 */
public interface CatalogRepository extends Repository<Catalog, Long> {

  Catalog findByJdUri(String href);
}

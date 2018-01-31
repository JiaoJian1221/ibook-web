package me.jiaojian.repository;

import me.jiaojian.domain.Book;

/**
 * Created by jiaojian on 2018/1/12.
 */
public interface BookRepository extends Repository<Book, Long> {

  Book findByJdUri(String href);
}

package me.jiaojian.controller;

import me.jiaojian.domain.Catalog;
import me.jiaojian.domain.projection.BookInfo;
import me.jiaojian.domain.projection.CatalogInfo;
import me.jiaojian.repository.BookRepository;
import me.jiaojian.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

import static me.jiaojian.repository.Repository.equal;

/**
 * Created by jiaojian on 2018/1/10.
 */
@RestController
@RequestMapping("/catalogs")
public class CatalogController {

  @Autowired
  private CatalogRepository catalogRepository;

  @Autowired
  private BookRepository bookRepository;

  private File dir = new File("/Users/jiaojian/Downloads/imgs/channel");

  @GetMapping
  public List<CatalogInfo> getCatalogs() {
    return catalogRepository.findAll(CatalogInfo.class);
  }

  @GetMapping("/{id}")
  public CatalogInfo getCatalog(@PathVariable Long id) {
    return catalogRepository.findOne(id, CatalogInfo.class);
  }

  @GetMapping("/{id}/books")
  public List<BookInfo> getBooks(@PathVariable Long id) {
    Catalog catalog = catalogRepository.findOne(id);
    return bookRepository.findAll(equal("catalog", catalog), BookInfo.class);
  }

}

package me.jiaojian.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jiaojian on 2018/1/8.
 */
@Entity
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String isbn;

  private String name;

  private String jdSku;
  private String jdUri;

  private String author;
  private String publisher;
  private String publishDate;

  @ManyToOne
  @JoinColumn(name = "catalog_id")
  private Catalog catalog;

  @OneToMany
  @JoinColumn(name = "obj_id", insertable = false, updatable = false)
  @Where(clause = "type = 'BOOK_IMG' or type = 'BOOK_THUMB_IMG'")
  private List<ImgUsage> usages;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getJdSku() {
    return jdSku;
  }

  public void setJdSku(String jdSku) {
    this.jdSku = jdSku;
  }

  public String getJdUri() {
    return jdUri;
  }

  public void setJdUri(String jdUri) {
    this.jdUri = jdUri;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }

  public Catalog getCatalog() {
    return catalog;
  }

  public void setCatalog(Catalog catalog) {
    this.catalog = catalog;
  }

  public List<ImgUsage> getUsages() {
    return usages;
  }

  public void setUsages(List<ImgUsage> usages) {
    this.usages = usages;
  }

  public ImgUsage getImg() {
    return usages.stream().filter(p -> p.isType(ImgUsage.Type.BOOK_IMG)).findFirst().orElse(null);
  }

  public ImgUsage getThumbImg() {
    return usages.stream().filter(p -> p.isType(ImgUsage.Type.BOOK_THUMB_IMG)).findFirst().orElse(null);
  }
}

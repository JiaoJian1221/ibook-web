package me.jiaojian.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jiaojian on 2018/1/4.
 */
@Entity
public class Catalog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "channel_id")
  private Channel channel;

  @OneToMany
  @JoinColumn(name = "catalog_id")
  private List<Book> books;

  private String jdUri;

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

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  public String getJdUri() {
    return jdUri;
  }

  public void setJdUri(String jdUri) {
    this.jdUri = jdUri;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }
}

package me.jiaojian.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by jiaojian on 2018/1/8.
 */
@Entity
public class Img {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

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

  public String getJdUri() {
    return jdUri;
  }

  public void setJdUri(String jdUri) {
    this.jdUri = jdUri;
  }
}

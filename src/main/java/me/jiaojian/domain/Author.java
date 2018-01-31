package me.jiaojian.domain;

import javax.persistence.*;

/**
 * Created by jiaojian on 2018/1/8.
 */
@Entity
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Type type;

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

  enum  Type {
    ORIGINAL, TRANSLATION
  }
}

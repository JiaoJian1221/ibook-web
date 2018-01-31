package me.jiaojian.domain;

import javax.persistence.*;

/**
 * Created by jiaojian on 2018/1/8.
 */
@Entity
public class ImgUsage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "img_id")
  private Img img;

  @Column(name = "obj_id")
  private Long objId;

  @Enumerated(EnumType.STRING)
  private Type type;

  public ImgUsage() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Img getImg() {
    return img;
  }

  public void setImg(Img img) {
    this.img = img;
  }

  public Long getObjId() {
    return objId;
  }

  public void setObjId(Long objId) {
    this.objId = objId;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public boolean isType(Type type) {
    return this.type == type;
  }

  public enum Type {
    CHANNEL_BANNER_IMG, BOOK_IMG, BOOK_THUMB_IMG
  }

}

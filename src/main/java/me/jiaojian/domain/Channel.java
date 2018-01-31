package me.jiaojian.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jiaojian on 2017/12/29.
 */
@Entity
public class Channel implements Serializable {

  private static final String USAGE_TYPE = "type = '" + ImgUsage.Type.CHANNEL_BANNER_IMG.name() + "'";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private boolean isSystemCatalog;

  private String jdUri;

  @OneToMany
  @JoinColumn(name = "obj_id", insertable = false, updatable = false)
  @Where(clause = "type = 'CHANNEL_BANNER_IMG'")
  private List<ImgUsage> usages;

  @OneToMany
  @JoinColumn(name = "channel_id", insertable = false, updatable = false)
  private List<Catalog> catalogs;

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

  public boolean isSystemCatalog() {
    return isSystemCatalog;
  }

  public void setSystemCatalog(boolean systemCatalog) {
    isSystemCatalog = systemCatalog;
  }

  public String getJdUri() {
    return jdUri;
  }

  public void setJdUri(String jdUri) {
    this.jdUri = jdUri;
  }

  public List<ImgUsage> getUsages() {
    return usages;
  }

  public void setUsages(List<ImgUsage> usages) {
    this.usages = usages;
  }


  public List<ImgUsage> getBannerImgs() {
    if(usages == null) {
      return Collections.emptyList();
    }
    return usages.stream()
      .filter(usage -> usage.isType(ImgUsage.Type.CHANNEL_BANNER_IMG))
      .collect(Collectors.toList());
  }

}

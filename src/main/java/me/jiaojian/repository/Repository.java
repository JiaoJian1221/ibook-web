package me.jiaojian.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jiaojian on 2017/12/29.
 */
@NoRepositoryBean
public interface Repository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

  static <T> Specification<T> like(String path, String value) {
    return (root, query, cb) -> cb.like(root.get(path), "%" + value + "%");
  }

  static <T> Specification<T> likeIgnorecase(String path, String value) {
    return (root, query, cb) -> cb.like(cb.upper(root.get(path)), "%" + value.toUpperCase() + "%");
  }

  static <T> Specification<T> equal(String path, Object value) {
    return (root, query, cb) -> cb.equal(root.get(path), value);
  }

  static <T> Specification<T> equalIgnorecase(String path, String value) {
    return (root, query, cb) -> cb.equal(cb.upper(root.get(path)), value.toUpperCase());
  }

  static <T, Y> Specification<T> in(String path, Collection<Y> values) {
    return (root, query, cb) -> root.<Y>get(path).in(values);
  }

  static <T> Specification<T> isTrue(String path) {
    return (root, query, cb) -> cb.isTrue(root.get(path));
  }

  static <T> Specification<T> isNull(String path) {
    return (root, query, cb) -> cb.isNull(root.get(path));
  }

  static <T> Specification<T> isNotNull(String path) {
    return (root, query, cb) -> cb.isNotNull(root.get(path));
  }

  static <T, Y extends Number> Specification<T> gt(String path, Y value) {
    return (root, query, cb) -> cb.gt(root.<Y>get(path), value);
  }

  static <T, Y extends Number> Specification<T> ge(String path, Y value) {
    return (root, query, cb) -> cb.ge(root.<Y>get(path), value);
  }

  static <T, Y extends Number> Specification<T> lt(String path, Y value) {
    return (root, query, cb) -> cb.lt(root.<Y>get(path), value);
  }

  static <T, Y extends Number> Specification<T> le(String path, Y value) {
    return (root, query, cb) -> cb.le(root.<Y>get(path), value);
  }

  static <T> Specification<T> not(Specification<T> specification) {
    return Specifications.not(specification);
  }

  static <T> Specification<T> and(Specification<T>... specifications) {
    return Stream.of(specifications).reduce((spec1, spec2) -> Specifications.where(spec1).and(spec2)).orElse(null);
  }

  static <T> Specification<T> or(Specification<T>... specifications) {
    return Stream.of(specifications).reduce((spec1, spec2) -> Specifications.where(spec1).or(spec2)).orElse(null);
  }

  ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
  default <P> P findOne(ID id, Class<P> type) {
    return Optional.ofNullable(findOne(id))
      .map(one -> projectionFactory.createProjection(type, one))
      .orElse(null) ;
  }
  default <P> List<P> findAll(Class<P> type) {
    return Optional.ofNullable(findAll()).orElse(Collections.emptyList())
      .stream().map(t -> projectionFactory.createProjection(type, t))
      .collect(Collectors.toList());
  }
  default <P> List<P> findAll(Specification<T> spec, Class<P> type) {
    return Optional.ofNullable(findAll(spec)).orElse(Collections.emptyList())
      .stream().map(t -> projectionFactory.createProjection(type, t))
      .collect(Collectors.toList());
  }
  default <P> Page<P> findAll(Specification<T> spec, Pageable pageable, Class<P> type) {
    return findAll(spec, pageable).map(t -> projectionFactory.createProjection(type, t));
  }
  default <P> List<P> findAll(Specification<T> spec, Sort sort, Class<P> type) {
    return Optional.ofNullable(findAll(spec, sort)).orElse(Collections.emptyList())
      .stream().map(t -> projectionFactory.createProjection(type, t))
      .collect(Collectors.toList());
  }

}

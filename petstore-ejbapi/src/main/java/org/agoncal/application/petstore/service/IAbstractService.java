package org.agoncal.application.petstore.service;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Tom on 4/13/2017.
 */
public interface IAbstractService<T> {
    T persist(T entity);

    T findById(Long id);

    void remove(T entity);

    T merge(T entity);

    List<T> listAll(Integer startPosition, Integer maxResult);

    List<T> listAll();

    TypedQuery<T> getListAllQuery();

    long count(T example);

    List<T> page(T example, int page, int pageSize);
}

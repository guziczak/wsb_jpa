package com.jpacourse.service.impl;

import com.jpacourse.persistance.dao.Dao;
import com.jpacourse.rest.exception.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Transactional
public abstract class AbstractService<T, E, ID extends Serializable> {

    protected abstract Dao<E, ID> getDao();

    protected abstract T mapToTO(E entity);

    public T findById(ID id) {
        E entity = getDao().findOne(id);
        if (entity == null) {
            throw new EntityNotFoundException(getEntityClass(), id);
        }
        return mapToTO(entity);
    }

    @SuppressWarnings("unchecked")
    protected Class<E> getEntityClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) type.getActualTypeArguments()[1];
    }

}
package com.jpacourse.rest.exception;

public class EntityNotFoundException extends RuntimeException {
    public <ID> EntityNotFoundException(Class<?> entityClass, ID id) {
        super("Could not find entity of instance " + entityClass.getSimpleName() + " with id " + id);
    }
}
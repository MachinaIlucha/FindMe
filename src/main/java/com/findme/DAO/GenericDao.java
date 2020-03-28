package com.findme.DAO;

import com.findme.Exceptions.InternalServerError;

public interface GenericDao<E> {
    E save(E t) throws InternalServerError;

    E read(Long id) throws InternalServerError;

    E update(E t) throws InternalServerError;

    void delete(E t) throws InternalServerError;
}

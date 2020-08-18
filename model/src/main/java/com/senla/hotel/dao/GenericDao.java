package com.senla.hotel.dao;

import com.senla.hotel.entity.AEntity;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T extends AEntity, ID extends Serializable> {

    T create(T object) throws PersistException;

    T getById(ID id) throws PersistException;

    void update(T object) throws PersistException;

    void delete(T object) throws PersistException;

    List<T> getAll() throws PersistException;
}

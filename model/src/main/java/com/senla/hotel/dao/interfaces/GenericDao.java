package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T extends AEntity, ID extends Serializable> {

    List<T> getAllSortedBy(SortField sortField) throws PersistException;

    void insertMany(List<T> list) throws PersistException;

    T create(T object) throws PersistException;

    T findById(ID id) throws PersistException;

    void update(T object) throws PersistException;

    void delete(T object) throws PersistException;

    List<T> getAll() throws PersistException;
}

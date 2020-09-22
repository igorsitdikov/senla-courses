package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.AEntity;
import com.senla.hotel.exceptions.PersistException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface GenericDao<T extends AEntity, ID extends Serializable> {

    void insertMany(List<T> list) throws PersistException;

    T create(T object) throws PersistException;

    T findById(ID id) throws PersistException;

    T update(T object) throws PersistException;

    void delete(T object) throws PersistException;

    List<T> getAll() throws PersistException;
}

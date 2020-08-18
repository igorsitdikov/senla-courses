package com.senla.hotel.dao;

import com.senla.hotel.entity.AEntity;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public interface GenericDao<T extends AEntity, ID extends Serializable> {

    /** Создает новую запись и соответствующий ей объект */
    T create();

    /** Создает новую запись, соответствующую объекту object */
    T persist(T object) throws PersistException;

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    T getById(ID id) throws PersistException;

    /** Сохраняет состояние объекта group в базе данных */
    void update(T object) throws PersistException;

    /** Удаляет запись об объекте из базы данных */
    void delete(T object) throws PersistException;

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    List<T> getAll() throws PersistException;
}

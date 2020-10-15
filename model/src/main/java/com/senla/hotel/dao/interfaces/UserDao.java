package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.UserEntity;
import com.senla.hotel.exceptions.PersistException;

public interface UserDao extends GenericDao<UserEntity, Long>{

    UserEntity findByEmail(String email) throws PersistException;
}

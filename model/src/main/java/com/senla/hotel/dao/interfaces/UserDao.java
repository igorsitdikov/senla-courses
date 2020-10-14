package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.User;
import com.senla.hotel.exceptions.PersistException;

public interface UserDao extends GenericDao<User, Long>{

    User findByEmail(String email) throws PersistException;
}

package com.senla.hotel.dao.interfaces;

import com.senla.hotel.entity.TokenEntity;
import com.senla.hotel.exceptions.PersistException;

public interface TokenDao extends GenericDao<TokenEntity, Long>{

    TokenEntity findByToken(String token) throws PersistException;
}


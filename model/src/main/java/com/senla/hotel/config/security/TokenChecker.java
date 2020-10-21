package com.senla.hotel.config.security;

import com.senla.hotel.dao.interfaces.TokenDao;
import com.senla.hotel.dao.interfaces.UserDao;
import com.senla.hotel.entity.TokenEntity;
import com.senla.hotel.exceptions.PersistException;
import org.springframework.stereotype.Component;

@Component
public class TokenChecker {

    private final TokenDao tokenDao;

    public TokenChecker(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    public TokenEntity validate(final String token) {
        try {
            return tokenDao.findByToken(token);
        } catch (PersistException e) {
            return null;
        }
    }
}

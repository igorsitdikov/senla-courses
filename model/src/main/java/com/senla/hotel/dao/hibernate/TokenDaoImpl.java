package com.senla.hotel.dao.hibernate;

import com.senla.hotel.dao.interfaces.TokenDao;
import com.senla.hotel.dao.interfaces.UserDao;
import com.senla.hotel.entity.TokenEntity;
import com.senla.hotel.entity.UserEntity;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class TokenDaoImpl extends AbstractDao<TokenEntity, Long> implements TokenDao {

    public TokenDaoImpl(HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }

    @Override
    public TokenEntity findByToken(final String token) throws PersistException {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            return getSingleBy("token", token, session);
        } catch (Exception e) {
            throw new PersistException(e.getMessage());
        }
    }
}

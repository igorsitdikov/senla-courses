package com.senla.hotel.dao.hibernate;

import com.senla.hotel.dao.interfaces.UserDao;
import com.senla.hotel.entity.UserEntity;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDao<UserEntity, Long> implements UserDao {

    public UserDaoImpl(HibernateUtil hibernateUtil) {
        super(hibernateUtil);
    }

    @Override
    public UserEntity findByEmail(final String email) throws PersistException {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            return getSingleBy("email", email, session);
        } catch (Exception e) {
            throw new PersistException(e.getMessage());
        }
    }
}

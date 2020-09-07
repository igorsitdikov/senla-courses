package com.senla.hotel.dao.hibernate;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.GenericDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Singleton
public abstract class AbstractDao<T extends AEntity, ID extends Long> implements GenericDao<T, ID> {

    public AbstractDao(final HibernateUtil hibernateUtil) {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.hibernateUtil = hibernateUtil;
    }

    private final Class<T> entityClass;

    protected HibernateUtil hibernateUtil;

    @Override
    public T findById(final Long id) throws PersistException {
        return getBy("id", id);
    }

    protected <E> T getBy(final String field, final E variable) throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            Root<T> root = criteria.from(entityClass);
            criteria.select(root).where(builder.equal(root.get(field), variable));
            Query<T> query = session.createQuery(criteria);
            return query.getSingleResult();
        }
    }

    @Override
    public List<T> getAll() throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            criteria.from(entityClass);
            return session.createQuery(criteria).getResultList();
        }
    }

    protected <E> List<T> getAllWhere(final String field, final E variable) throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            Root<T> root = criteria.from(entityClass);
            criteria.select(root).where(builder.equal(root.get(field), variable));
            Query<T> query = session.createQuery(criteria);
            return query.getResultList();
        }
    }

    @Override
    public void insertMany(final List<T> list) throws PersistException {
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            int i = 0;
            for (T entity : list) {
                session.persist(entity);
                i++;
                if (i % 50 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
        }
    }

    @Override
    public T create(final T object) throws PersistException {
        if (object.getId() != null) {
            throw new PersistException("Object is already persist.");
        }
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            session.flush();
            transaction.commit();
        }
        return object;
    }

    @Override
    public void update(final T object) throws PersistException {
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(object);
            session.flush();
            transaction.commit();
        }
    }

    @Override
    public void delete(final T object) throws PersistException {
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(object);
            session.flush();
            transaction.commit();
        }
    }
}


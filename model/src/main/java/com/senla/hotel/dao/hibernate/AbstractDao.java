package com.senla.hotel.dao.hibernate;

import com.senla.hotel.dao.interfaces.GenericDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.enumerated.SortField;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public abstract class AbstractDao<T extends AEntity, ID extends Long> implements GenericDao<T, ID> {

    private final Class<T> entityClass;
    private final static Integer BATCH_SIZE = 50;
    protected HibernateUtil hibernateUtil;

    public AbstractDao(final HibernateUtil hibernateUtil) {
        this.entityClass =
                (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.hibernateUtil = hibernateUtil;
    }

    @Override
    public T findById(final Long id) throws PersistException {
        return getSingleBy("id", id);
    }

    protected <E> T getSingleBy(final String field, final E variable) throws PersistException {
        Query<T> query = getQueryBy(field, variable);
        return query.getSingleResult();
    }

    protected <E> Query<T> getQueryBy(final String field, final E variable, final SortField sortField) throws PersistException {
        try (Session session = hibernateUtil.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            Root<T> root = criteria.from(entityClass);
            criteria
                    .select(root)
                    .where(builder.equal(root.get(field), variable))
                    .orderBy(builder.asc(root.get(sortField.getFieldName())));
            return session.createQuery(criteria);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    private <E> Query<T> getQueryBy(final String field, final E variable) throws PersistException {
        return getQueryBy(field, variable, SortField.DEFAULT);
    }

    @Override
    public List<T> getAll() throws PersistException {
        return getAllSortedBy(SortField.DEFAULT);
    }

    protected <E> List<T> getAllBy(final String field, final E variable) throws PersistException {
        Query<T> query = getQueryBy(field, variable, SortField.DEFAULT);
        return query.getResultList();
    }

    protected List<T> getAllSortedBy(final SortField sortField) throws PersistException {
        try (Session session = hibernateUtil.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            Root<T> root = criteria.from(entityClass);
            criteria
                    .select(root)
                    .orderBy(builder.asc(root.get(sortField.getFieldName())));
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    protected <E> List<T> getAllBy(final String field, final E variable, final SortField sortField) throws PersistException {
        Query<T> query = getQueryBy(field, variable, sortField);
        return query.getResultList();
    }

    @Override
    public void insertMany(final List<T> list) throws PersistException {
        Transaction transaction = null;
        try (Session session = hibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            int i = 0;
            for (T entity : list) {
                session.persist(entity);
                i++;
                if (i % BATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PersistException(e);
        }
    }

    @Override
    public T create(final T object) throws PersistException {
        if (object.getId() != null) {
            throw new PersistException("Object is already persist.");
        }
        Transaction transaction = null;
        try (Session session = hibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            session.save(object);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PersistException(e);
        }
        return object;
    }

    @Override
    public void update(final T object) throws PersistException {
        Transaction transaction = null;
        try (Session session = hibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            session.update(object);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PersistException(e);
        }
    }

    @Override
    public void delete(final T object) throws PersistException {
        Transaction transaction = null;
        try (Session session = hibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            session.delete(object);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new PersistException(e);
        }
    }
}

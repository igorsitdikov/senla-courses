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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Singleton
public abstract class AbstractDao<T extends AEntity, ID extends Long> implements GenericDao<T, ID> {

    public AbstractDao(final HibernateUtil hibernateUtil) {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.hibernateUtil = hibernateUtil;
    }

    @PersistenceContext
    private EntityManager entityManager;

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
            List<T> data = query.getResultList();
            System.out.println();
            final T next = data.iterator().next();
            return next;
        }

//        final List<T> list;
//        String sql = getSelectQuery();
//        sql += " WHERE " + field + " = ?";
//        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
//            setVariableToStatement(statement, variable);
//            final ResultSet rs = statement.executeQuery();
//            list = parseResultSet(rs);
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
//        if (list == null || list.size() == 0) {
//            throw new PersistException("Entity not found.");
//        }
//        if (list.size() > 1) {
//            throw new PersistException("Received more than one record.");
//        }
//        return list.iterator().next();
//        return null;
    }

    @SafeVarargs
    protected final <E> void setVariableToStatement(final PreparedStatement statement, final E... variable)
        throws SQLException {
        for (int i = 0; i < variable.length; i++) {
            if (variable[i] instanceof Long) {
                statement.setLong(i + 1, (Long) variable[i]);
            } else if (variable[i] instanceof BigDecimal) {
                statement.setBigDecimal(i + 1, (BigDecimal) variable[i]);
            } else if (variable[i] instanceof Double) {
                statement.setDouble(i + 1, (Double) variable[i]);
            } else if (variable[i] instanceof Integer) {
                statement.setInt(i + 1, (Integer) variable[i]);
            } else if (variable[i] instanceof String) {
                statement.setString(i + 1, (String) variable[i]);
            } else if (variable[i] instanceof Enum) {
                statement.setString(i + 1, variable[i].toString());
            } else if (variable[i] instanceof LocalDate) {
                statement.setDate(i + 1, Date.valueOf((LocalDate) variable[i]));
            }
        }
    }

    @Override
    public List<T> getAll() throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            criteria.from(entityClass);
            List<T> data = session.createQuery(criteria).getResultList();
            return data;
        }

//        final List<T> list;
//        final String sql = getSelectQuery();
//        try {
//            final PreparedStatement statement = connector.getConnection().prepareStatement(sql);
//            final ResultSet rs = statement.executeQuery();
//            list = parseResultSet(rs);
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
//        return list;
//        return null;
    }

    protected <E> List<T> getAllWhere(final String field, final E variable) throws PersistException {
        SessionFactory factory = hibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityClass);
            Root<T> root = criteria.from(entityClass);
            criteria.select(root).where(builder.equal(root.get(field), variable));
            Query<T> query = session.createQuery(criteria);
            List<T> data = query.getResultList();
            return data;
        }
    }

    @SafeVarargs
    protected final <E> List<T> getAllBySqlQuery(final String sql, final E... variables) throws PersistException {
//        final List<T> list;
//        try {
//            final PreparedStatement statement = connector.getConnection().prepareStatement(sql);
//            setVariableToStatement(statement, variables);
//            final ResultSet rs = statement.executeQuery();
//            list = parseResultSet(rs);
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
        return null;
    }

    @Override
    public void insertMany(final List<T> list) throws PersistException {
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            int i = 0;
            for (T entity: list) {
                session.persist(entity);
                i++;
                if (i%50==0){
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
        }
//        String sql = getCreateQuery();
//        try (PreparedStatement statement = connector.getConnection()
//            .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            for (final T object : list) {
//                prepareStatementForInsert(statement, object);
//                statement.addBatch();
//            }
//            statement.executeBatch();
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
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

//        final String sql = getCreateQuery();
//        try (PreparedStatement statement = connector.getConnection()
//            .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            prepareStatementForInsert(statement, object);
//            final int count = statement.executeUpdate();
//            if (count != 1) {
//                throw new PersistException("On persist modify more then 1 record: " + count);
//            }
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    object.setId(generatedKeys.getLong(1));
//                } else {
//                    throw new PersistException("Creating user failed, no ID obtained.");
//                }
//            }
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }

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
//        final String sql = getUpdateQuery();
//        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
//            prepareStatementForUpdate(statement, object);
//            final int count = statement.executeUpdate();
//            if (count != 1) {
//                throw new PersistException("On update modify more then 1 record: " + count);
//            }
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
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
//        final String sql = getDeleteQuery();
//        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
//            setVariableToStatement(statement, object.getId());
//            final int count = statement.executeUpdate();
//            if (count != 1) {
//                throw new PersistException("On delete modify more then 1 record: " + count);
//            }
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
    }
}


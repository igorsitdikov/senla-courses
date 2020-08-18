package com.senla.hotel.dao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.utils.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Singleton
public abstract class AbstractDao<T extends AEntity, ID extends Long> implements GenericDao<T, ID> {
    public AbstractDao(Connector connector) {
        this.connector = connector;
    }

    public abstract String getSelectQuery();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    protected Connector connector;

    @Override
    public T getById(Long id) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public T create(T object) throws PersistException {
        if (object.getId() != null) {
            throw new PersistException("Object is already persist.");
        }
        String sql = getCreateQuery();
        try (PreparedStatement statement = connector.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    object.setId(generatedKeys.getLong(1));
                } else {
                    throw new PersistException("Creating user failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }

        return object;
    }

    @Override
    public void update(T object) throws PersistException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void delete(T object) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

//    public AbstractJDBCDao(DaoFactory<Connection> parentFactory, Connection connection) {
//        this.parentFactory = parentFactory;
//        this.connection = connection;
//    }
//
//    protected Identified getDependence(Class<? extends Identified> dtoClass, Serializable pk) throws PersistException {
//        return parentFactory.getDao(connection, dtoClass).getByPK(pk);
//    }
//
//    protected boolean addRelation(Class<? extends Identified> ownerClass, String field) {
//        try {
//            return relations.add(new ManyToOne(ownerClass, parentFactory, field));
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void saveDependences(Identified owner) throws PersistException {
//        for (ManyToOne m : relations) {
//            try {
//                if (m.getDependence(owner) == null) {
//                    continue;
//                }
//                if (m.getDependence(owner).getId() == null) {
//                    Identified depend = m.persistDependence(owner, connection);
//                    m.setDependence(owner, depend);
//                } else {
//                    m.updateDependence(owner, connection);
//                }
//            } catch (Exception e) {
//                throw new PersistException("Exception on save dependence in relation " + m + ".", e);
//            }
//        }
//    }
}


package com.senla.hotel.dao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.interfaces.GenericDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.entity.Attendance;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.Connector;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return getBy("id", id);
    }

    protected <E> T getBy(String field, E variable) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE " + field + " = ?";
        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
            setVariableToStatement(variable, statement);
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

    protected <E> void setVariableToStatement(E variable, PreparedStatement statement) throws SQLException {
        if (variable instanceof Long) {
            statement.setLong(1, (Long) variable);
        } else if (variable instanceof BigDecimal) {
            statement.setBigDecimal(1, (BigDecimal) variable);
        } else if (variable instanceof Double) {
            statement.setDouble(1, (Double) variable);
        } else if (variable instanceof Integer) {
            statement.setInt(1, (Integer) variable);
        } else if (variable instanceof String) {
            statement.setString(1, (String) variable);
        } else if (variable instanceof Enum) {
            statement.setString(1, variable.toString());
        }
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

    protected <E> List<T> getAllWhere(String field, E variable) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE " + field + " = ?";
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement(sql);
            setVariableToStatement(variable, statement);
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
}


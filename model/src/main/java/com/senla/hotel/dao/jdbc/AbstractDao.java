package com.senla.hotel.dao.jdbc;

import com.senla.hotel.dao.interfaces.GenericDao;
import com.senla.hotel.entity.AEntity;
import com.senla.hotel.exceptions.PersistException;
import com.senla.hotel.utils.Connector;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public abstract class AbstractDao<T extends AEntity, ID extends Long> implements GenericDao<T, ID> {

    public AbstractDao(final Connector connector) {
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
    public T findById(final Long id) throws PersistException {
        return getSingleBy("id", id);
    }

    protected <E> T getSingleBy(final String field, final E variable) throws PersistException {
        final List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE " + field + " = ?";
        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
            setVariableToStatement(statement, variable);
            final ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            throw new PersistException("Entity not found.");
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
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
        final List<T> list;
        final String sql = getSelectQuery();
        try {
            final PreparedStatement statement = connector.getConnection().prepareStatement(sql);
            final ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    protected <E> List<T> getAllBy(final String field, final E variable) throws PersistException {
        String sql = getSelectQuery();
        sql += " WHERE " + field + " = ?";
        return getAllBySqlQuery(sql, variable);
    }

    @SafeVarargs
    protected final <E> List<T> getAllBySqlQuery(final String sql, final E... variables) throws PersistException {
        final List<T> list;
        try {
            final PreparedStatement statement = connector.getConnection().prepareStatement(sql);
            setVariableToStatement(statement, variables);
            final ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (final Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public void insertMany(final List<T> list) throws PersistException {
        String sql = getCreateQuery();
        try (PreparedStatement statement = connector.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (final T object : list) {
                prepareStatementForInsert(statement, object);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public T create(final T object) throws PersistException {
        if (object.getId() != null) {
            throw new PersistException("Object is already persist.");
        }
        final String sql = getCreateQuery();
        try (PreparedStatement statement = connector.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, object);
            final int count = statement.executeUpdate();
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
        } catch (final Exception e) {
            throw new PersistException(e);
        }

        return object;
    }

    @Override
    public void update(final T object) throws PersistException {
        final String sql = getUpdateQuery();
        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
            prepareStatementForUpdate(statement, object);
            final int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void delete(final T object) throws PersistException {
        final String sql = getDeleteQuery();
        try (PreparedStatement statement = connector.getConnection().prepareStatement(sql)) {
            setVariableToStatement(statement, object.getId());
            final int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (final Exception e) {
            throw new PersistException(e);
        }
    }

//    @Override
//    public List<T> getAllSortedBy(final SortField sortField) throws PersistException {
//        final List<T> list;
//        final String sql = getSelectQuery() + " ORDER BY " + sortField.getFieldName() + " ASC";
//        try {
//            final PreparedStatement statement = connector.getConnection().prepareStatement(sql);
//            final ResultSet rs = statement.executeQuery();
//            list = parseResultSet(rs);
//        } catch (final Exception e) {
//            throw new PersistException(e);
//        }
//        return list;
//    }
}


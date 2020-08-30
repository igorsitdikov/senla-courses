package com.senla.hotel.utils;

import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class Connector {

    private static final Logger logger = LogManager.getLogger(Connector.class);

    @PropertyLoad
    private String url;
    @PropertyLoad
    private String user;
    @PropertyLoad
    private String password;
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private void createConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (final SQLException sqlEx) {
            logger.error("Failed to connect to database!");
        }
    }
}

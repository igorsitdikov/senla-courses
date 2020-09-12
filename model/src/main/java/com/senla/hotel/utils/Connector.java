package com.senla.hotel.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class Connector {

    private static final Logger logger = LogManager.getLogger(Connector.class);

    @Value(value = "url")
    private String url;
    @Value(value = "user")
    private String user;
    @Value(value = "password")
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

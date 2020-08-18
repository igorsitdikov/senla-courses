package com.senla.hotel.utils;

import com.senla.hotel.annotation.PropertyLoad;
import com.senla.hotel.annotation.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class Connector {
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
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }


}

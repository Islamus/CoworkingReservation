package org.example.coworking;

import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/coworking";
    private static final String USER = "postgres";
    private static final String PASSWORD = "9293709b13";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

}

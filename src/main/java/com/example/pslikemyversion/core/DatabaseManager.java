package com.example.pslikemyversion.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/showdown";
    private static final String USER = "root";
    private static final String PASSWORD = "demopma"; // À remplir selon ta config locale

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

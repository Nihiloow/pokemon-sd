package com.example.pslikemyversion.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/pokemon_showdown";
    private static final String USER = "root";
    private static final String PASSWORD = "demopma"; // change for local db (.env wip)

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

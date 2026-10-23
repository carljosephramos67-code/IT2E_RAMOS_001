package com.gymmembership.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:sqlite:scenario.db";
    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            connection.close();
            throw e;
        }
        return connection;
    }

    public static void initializeDatabase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS gym_members (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "full_name TEXT NOT NULL, " +
                "tier TEXT NOT NULL CHECK (tier IN ('BASIC','PREMIUM','VIP')), " +
                "monthly_fee REAL NOT NULL CHECK (monthly_fee >= 0), " +
                "status TEXT NOT NULL CHECK (status IN ('ACTIVE','INACTIVE')))";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}

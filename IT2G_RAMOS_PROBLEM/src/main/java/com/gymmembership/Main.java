package com.gymmembership;

import com.gymmembership.dao.DatabaseConnection;
import com.gymmembership.dao.ScenarioDao;
import com.gymmembership.ui.ScenarioMenu;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();
            try (Scanner scanner = new Scanner(System.in)) {
                new ScenarioMenu(scanner, new ScenarioDao()).run();
            }
        } catch (SQLException e) {
            System.err.println("Unable to initialize database: " + e.getMessage());
        }
    }
}

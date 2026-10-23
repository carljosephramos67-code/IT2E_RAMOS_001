package com.gymmembership.ui;

import com.gymmembership.dao.ScenarioDao;
import com.gymmembership.model.ScenarioEntity;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final Scanner scanner;
    private final ScenarioDao dao;

    public ScenarioMenu(Scanner scanner, ScenarioDao dao) {
        this.scanner = scanner;
        this.dao = dao;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ", 1, 3);
            switch (choice) {
                case 1 -> addMember();
                case 2 -> viewMembers();
                case 3 -> { System.out.println("\nExiting system... Thank you!"); running = false; }
                default -> { }
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==============================================================");
        System.out.println("                 GYM MEMBERSHIP MANAGEMENT");
        System.out.println("==============================================================");
        System.out.println("[1] Add New Member");
        System.out.println("[2] View All Members");
        System.out.println("[3] Exit System");
        System.out.println("==============================================================");
    }

    private void addMember() {
        System.out.println("\n--- ADD NEW MEMBER ---");
        String name;
        do {
            System.out.print("Enter Full Name: ");
            name = scanner.nextLine().trim();
            if (name.isBlank()) System.out.println("Full name cannot be empty.");
        } while (name.isBlank());

        String tier = readChoice("Enter Tier (BASIC / PREMIUM / VIP): ", "BASIC", "PREMIUM", "VIP");
        double fee = readDouble("Enter Monthly Fee: ");
        String status = readChoice("Enter Status (ACTIVE / INACTIVE): ", "ACTIVE", "INACTIVE");
        try {
            dao.addMember(name, tier, fee, status);
            System.out.println("\nSUCCESS: Member record saved to database!");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not save member record. " + e.getMessage());
        }
    }

    private void viewMembers() {
        System.out.println("\n                         ALL GYM MEMBERS");
        try {
            List<ScenarioEntity> members = dao.getAllMembers();
            String border = "+------+----------------------+----------+--------------+------------+";
            System.out.println(border);
            System.out.printf("| %-4s | %-20s | %-8s | %-12s | %-10s |%n",
                    "ID", "FULL NAME", "TIER", "MONTHLY FEE", "STATUS");
            System.out.println(border);
            for (ScenarioEntity m : members) {
                System.out.printf("| %-4d | %-20.20s | %-8s | %12.2f | %-10s |%n",
                        m.id(), m.fullName(), m.tier(), m.monthlyFee(), m.status());
            }
            System.out.println(border);
            System.out.println("Total Records Displayed: " + members.size());
        } catch (SQLException e) {
            System.out.println("ERROR: Could not load records. " + e.getMessage());
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int n = Integer.parseInt(scanner.nextLine().trim());
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException ignored) { }
            System.out.printf("Please enter a number from %d to %d.%n", min, max);
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double n = Double.parseDouble(scanner.nextLine().trim());
                if (Double.isFinite(n) && n >= 0) return n;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a valid fee (0 or higher), e.g. 2500.00.");
        }
    }

    private String readChoice(String prompt, String... allowed) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim().toUpperCase();
            for (String option : allowed) if (option.equals(value)) return value;
            System.out.println("Invalid choice. Allowed values: " + String.join(" / ", allowed));
        }
    }
}

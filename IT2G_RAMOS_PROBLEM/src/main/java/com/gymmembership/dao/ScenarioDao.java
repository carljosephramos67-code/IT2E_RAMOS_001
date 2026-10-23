package com.gymmembership.dao;

import com.gymmembership.model.ScenarioEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {
    public void addMember(String fullName, String tier, double monthlyFee, String status) throws SQLException {
        String sql = "INSERT INTO gym_members (full_name, tier, monthly_fee, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fullName);
            statement.setString(2, tier);
            statement.setDouble(3, monthlyFee);
            statement.setString(4, status);
            statement.executeUpdate();
        }
    }

    public List<ScenarioEntity> getAllMembers() throws SQLException {
        String sql = "SELECT id, full_name, tier, monthly_fee, status FROM gym_members ORDER BY id";
        List<ScenarioEntity> members = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                members.add(new ScenarioEntity(rs.getInt("id"), rs.getString("full_name"),
                        rs.getString("tier"), rs.getDouble("monthly_fee"), rs.getString("status")));
            }
        }
        return members;
    }
}

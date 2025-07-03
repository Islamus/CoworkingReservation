package org.example.dao;

import org.example.WorkSpace;
import org.example.coworking.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceDAO {

    public void insertWorkspace(WorkSpace ws) throws SQLException {
        String sql = "INSERT INTO workspaces (id, type, price, available) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, ws.getId());
            stmt.setString(2, ws.getType());
            stmt.setDouble(3, ws.getPrice());
            stmt.setBoolean(4, ws.isAvailable());
            stmt.executeUpdate();
        }
    }

    public List<WorkSpace> getAvailableWorkspaces() throws SQLException {
        String sql = "SELECT * FROM workspaces WHERE available = true";
        List<WorkSpace> list = new ArrayList<>();

        try (Statement stmt = DBConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                WorkSpace ws = new WorkSpace(
                        rs.getString("id"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("available")
                );
                list.add(ws);
            }
        }
        return list;
    }

    public int cancelWorkspaces(String id) throws SQLException {
        String sql = "DELETE FROM workspaces WHERE id = ?";


        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
            stmt.setString(1, id);
            return stmt.executeUpdate();

        }

    }
}


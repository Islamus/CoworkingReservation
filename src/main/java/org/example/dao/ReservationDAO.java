package org.example.dao;

import org.example.Reservation;
import org.example.coworking.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public void insertReservation(Reservation r) throws SQLException {
        String sql = "INSERT INTO bookings (workspace_id, user_name, booking_date, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, r.getWorkspaceId());
            stmt.setString(2, r.getUserName());
            stmt.setDate(3, Date.valueOf(r.getDate()));
            stmt.setTime(4, Time.valueOf(r.getStartTime()));
            stmt.setTime(5, Time.valueOf(r.getEndTime()));
            stmt.executeUpdate();
        }
    }

    public List<Reservation> getUserReservations(String userName) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE user_name = ?";
        List<Reservation> reservations = new ArrayList<>();

        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation r = new Reservation(
                            String.valueOf(rs.getInt("booking_id")),
                            rs.getString("user_name"),
                            rs.getString("workspace_id"),
                            rs.getDate("booking_date").toString(),
                            rs.getTime("start_time").toString(),
                            rs.getTime("end_time").toString()
                    );
                    reservations.add(r);
                }
            }
        }

        return reservations;
    }

    public int cancelUserReservation(String userName) throws SQLException{
        String sql = "DELETE FROM bookings WHERE user_name = ?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, userName);
            return stmt.executeUpdate();

        }
    }

}

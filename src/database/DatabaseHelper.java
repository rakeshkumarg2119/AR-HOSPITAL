package database;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class DatabaseHelper {

    private static Connection conn = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/ar_hospital";
    private static final String USER = "aradmin";
    private static final String PASSWORD = "secret";

    static {
        try {
            Class.forName("org.postgresql.Driver"); // Load driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        createTableIfNotExists();
        populateDefaultData();
        createAmbulanceTables();
    }

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            return conn;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cannot connect to database. Check your server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableIfNotExists() {
        String staffSql = "CREATE TABLE IF NOT EXISTS staff (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "age INT, " +
                "email VARCHAR(100), " +
                "department VARCHAR(100), " +
                "position VARCHAR(100)" +
                ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(staffSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void populateDefaultData() {
        String checkSql = "SELECT COUNT(*) FROM staff";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {

            if (rs.next() && rs.getInt(1) == 0) {
                // Default staff entries
                insertStaff("Dr. Amuthan", 30, "24suca02@tcarts.in", "Cardiology", "Chief Doctor");
                insertStaff("Mr. RakeshKumar", 20, "24suca17@tcarts.in", "Administration", "HR Manager");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertStaff(String name, int age, String email, String department, String position) {
        String sql = "INSERT INTO staff (name, age, email, department, position) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, email);
            stmt.setString(4, department);
            stmt.setString(5, position);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadAllStaff(DefaultTableModel tableModel) {
        String sql = "SELECT name, age, email, department, position FROM staff";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getString("position")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAmbulanceTables() {
        String driverSql = "CREATE TABLE IF NOT EXISTS ambulance_drivers (" +
                "id SERIAL PRIMARY KEY, " +
                "driver_name VARCHAR(100) NOT NULL, " +
                "age INT, " +
                "vehicle_no VARCHAR(50), " +
                "num_assistants INT, " +
                "ambulance_type VARCHAR(100), " +
                "phone_no VARCHAR(20), " +
                "status VARCHAR(10) DEFAULT 'IN'" +
                ");";

        String caseSql = "CREATE TABLE IF NOT EXISTS ambulance_cases (" +
                "id SERIAL PRIMARY KEY, " +
                "patient_name VARCHAR(100), " +
                "address VARCHAR(255), " +
                "phone VARCHAR(20), " +
                "vehicle_no VARCHAR(50)" +
                ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(driverSql);
            stmt.execute(caseSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAmbulanceDriver(String name, int age, String vehicleNo,
                                             int assistants, String type, String phone) {
        String sql = "INSERT INTO ambulance_drivers (driver_name, age, vehicle_no, num_assistants, ambulance_type, phone_no, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, 'IN')";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, vehicleNo);
            stmt.setInt(4, assistants);
            stmt.setString(5, type);
            stmt.setString(6, phone);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDriverStatus(String vehicleNo, String status) {
        String sql = "UPDATE ambulance_drivers SET status = ? WHERE vehicle_no = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, vehicleNo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAmbulanceCase(String patient, String address, String phone, String vehicleNo) {
        String sql = "INSERT INTO ambulance_cases (patient_name, address, phone, vehicle_no) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.setString(4, vehicleNo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadAmbulanceDrivers(DefaultTableModel model) {
        String sql = "SELECT * FROM ambulance_drivers";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            model.setRowCount(0);
            while (rs.next()) {
                String dbStatus = rs.getString("status");
                String displayStatus = "IN".equalsIgnoreCase(dbStatus) ? "Allocate" : "Allocated";

                model.addRow(new Object[]{
                        rs.getString("driver_name"),
                        rs.getInt("age"),
                        rs.getString("vehicle_no"),
                        rs.getInt("num_assistants"),
                        rs.getString("ambulance_type"),
                        rs.getString("phone_no"),
                        displayStatus
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadAmbulanceCases(DefaultTableModel model) {
        String sql = "SELECT patient_name, address, phone, vehicle_no FROM ambulance_cases";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("patient_name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("vehicle_no")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

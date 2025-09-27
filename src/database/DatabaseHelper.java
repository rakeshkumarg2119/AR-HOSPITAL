package database;


import java.io.File;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class DatabaseHelper {

	private static Connection conn  = null;
   // private static final String URL = "jdbc:h2:file:C:/Users/HP/test;AUTO_SERVER=TRUE"; // or "jdbc:h2:mem:testdb" for in-memory
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static String getDatabaseUrl() {
        String userHome = System.getProperty("user.home");
        java.nio.file.Path dbPath = java.nio.file.Paths.get(userHome, ".data", "test");
        dbPath.getParent().toFile().mkdirs();
        return "jdbc:h2:file:" + dbPath.toString() + ";AUTO_SERVER=TRUE";
    }

    static {
    	 File dir = new File(System.getProperty("user.home") + "/data");
         if (!dir.exists()) dir.mkdirs();
        createTableIfNotExists(); // run once when class is loaded
        populateDefaultData(); 
        createAmbulanceTables();
    }

    public static Connection getConnection() {
        try {
        	 if (conn == null || conn.isClosed()) {
                //conn=DriverManager.getConnection(URL, USER, PASSWORD);
                conn = DriverManager.getConnection(getDatabaseUrl(), USER, PASSWORD);
        	 }
        	 return conn;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database is already in use. Please close other instances.", "Connection Error", JOptionPane.ERROR_MESSAGE);
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
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS staff (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "age INT, " +
                    "email VARCHAR(100), " +
                    "department VARCHAR(100), " +
                    "position VARCHAR(100)" +
                    ")";
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void populateDefaultData() {
        String checkSql = "SELECT COUNT(*) FROM staff";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {

            if (rs.next() && rs.getInt(1) == 0) {
                // If the table is empty, insert default records
                insertStaff("Dr. Amuthan", 30, "24suca02@tcarts.in", "Cardiology", "Cheif Doctor");
                insertStaff("Mr. RakeshKumar",20 , "24suca17@tcarts.in", "Administration", "HR Manager");
            
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertStaff(String name, int age, String email, String department, String position) {
        String sql = "INSERT INTO staff (name, age, email, department, position) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, email);
            stmt.setString(4, department);
            stmt.setString(5, position);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadAllStaff(DefaultTableModel tableModel) {
        String sql = "SELECT name, age, email, department, position FROM staff";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            tableModel.setRowCount(0); // clear existing
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getString("position")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void createAmbulanceTables() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Driver Table
        	stmt.execute("CREATE TABLE IF NOT EXISTS ambulance_drivers (" +
        		    "id IDENTITY PRIMARY KEY, " +
        		    "driver_name VARCHAR(100), " +
        		    "age INT, " +
        		    "vehicle_no VARCHAR(50), " +
        		    "num_assistants INT, " +
        		    "ambulance_type VARCHAR(100), " +
        		    "phone_no VARCHAR(20), " +
        		    "status VARCHAR(10) DEFAULT 'IN')");

            // Case Table
        	stmt.execute("CREATE TABLE IF NOT EXISTS ambulance_cases (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "patient_name VARCHAR(100), " +
                    "address VARCHAR(255), " +
                    "phone VARCHAR(20), " +
                    "vehicle_no VARCHAR(50))");
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateDriverStatus(String vehicleNo, String status) {
        String sql = "UPDATE ambulance_drivers SET status = ? WHERE vehicle_no = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, vehicleNo);
            stmt.executeUpdate();
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadAmbulanceDrivers(DefaultTableModel model) {
        String sql = "SELECT * FROM ambulance_drivers";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            model.setRowCount(0);  // Clear old data
            while (rs.next()) {
                // Convert 'IN' to 'Allocate', 'OUT' to 'Allocated'
                String dbStatus = rs.getString("status");
                String displayStatus = "IN".equalsIgnoreCase(dbStatus) ? "Allocate" : "Allocated";

                model.addRow(new Object[] {
                   // rs.getInt("id"),
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
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("patient_name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("vehicle_no")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


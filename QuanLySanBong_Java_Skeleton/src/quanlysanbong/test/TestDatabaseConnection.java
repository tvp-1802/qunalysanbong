package quanlysanbong.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Test class to verify MySQL connection
 * Run this to check if JDBC driver is properly configured
 */
public class TestDatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/quanlysanbong?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "";
        
        try {
            System.out.println("Attempting to connect to MySQL...");
            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            
            Connection conn = DriverManager.getConnection(url, user, password);
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("\n✓ CONNECTION SUCCESSFUL!");
                System.out.println("Database: quanlysanbong");
                System.out.println("Connected as: " + user);
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("\n✗ CONNECTION FAILED!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("\nPossible causes:");
            System.out.println("1. MySQL Connector/J not in classpath");
            System.out.println("2. MySQL server not running");
            System.out.println("3. Wrong database credentials");
            System.out.println("4. Database doesn't exist");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("\n✗ UNEXPECTED ERROR!");
            e.printStackTrace();
        }
    }
}

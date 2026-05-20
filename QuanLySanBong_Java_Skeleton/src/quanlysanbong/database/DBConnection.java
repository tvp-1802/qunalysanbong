package quanlysanbong.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlysanbong?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

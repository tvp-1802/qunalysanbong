package quanlysanbong.database;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Stream;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlysanbong?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Driver mysqlDriver;

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);

        Connection conn = getMysqlDriver().connect(URL, props);
        if (conn == null) {
            throw new SQLException("Khong ket noi duoc MySQL. Kiem tra XAMPP (MySQL dang chay) va database quanlysanbong.");
        }
        return conn;
    }

    private static synchronized Driver getMysqlDriver() throws SQLException {
        if (mysqlDriver != null) {
            return mysqlDriver;
        }

        try {
            Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
            mysqlDriver = (Driver) clazz.getDeclaredConstructor().newInstance();
            return mysqlDriver;
        } catch (ReflectiveOperationException ignored) {
            // Driver khong co tren classpath (NetBeans Run File / IDE khac)
        }

        File jar = findConnectorJar();
        if (jar == null) {
            throw new SQLException(
                    "Khong tim thay MySQL JDBC driver. "
                            + "Dat file mysql-connector-j-9.7.0.jar vao thu muc lib/ cua project, "
                            + "sau do Clean and Build va chay bang F6 (Run Project), khong dung Run File.");
        }

        try {
            URLClassLoader loader = new URLClassLoader(
                    new URL[]{jar.toURI().toURL()},
                    DBConnection.class.getClassLoader());
            Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver", true, loader);
            mysqlDriver = (Driver) clazz.getDeclaredConstructor().newInstance();
            return mysqlDriver;
        } catch (Exception e) {
            throw new SQLException("Khong nap duoc MySQL JDBC driver tu: " + jar.getAbsolutePath(), e);
        }
    }

    private static File findConnectorJar() {
        Path start = Paths.get(System.getProperty("user.dir"));
        for (int depth = 0; depth < 6 && start != null; depth++, start = start.getParent()) {
            Path libDir = start.resolve("lib");
            if (!Files.isDirectory(libDir)) {
                continue;
            }
            try (Stream<Path> files = Files.list(libDir)) {
                Path jar = files
                        .filter(Files::isRegularFile)
                        .filter(p -> {
                            String name = p.getFileName().toString().toLowerCase();
                            return name.startsWith("mysql-connector") && name.endsWith(".jar");
                        })
                        .findFirst()
                        .orElse(null);
                if (jar != null) {
                    return jar.toFile();
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package taxi.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PROPERTIES_FILE_NAME = "db.properties";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found", e);
        }
    }

    public static Connection getConnection() {
        Properties databaseProperties = new Properties();
        try {
            InputStream inputStream = ConnectionUtil.class
                    .getClassLoader()
                    .getResourceAsStream(PROPERTIES_FILE_NAME);
            databaseProperties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load database properties", e);
        }
        try {
            return DriverManager.getConnection(
                    databaseProperties.getProperty("url"),
                    databaseProperties.getProperty("username"),
                    databaseProperties.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException("Could not establish connection", e);
        }
    }
}

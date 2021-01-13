package manufacturing.util;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionController {
    private static final String PROPERTIES_FILE_NAME = "src/main/resources/db.properties";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find PostgreSQL driver", e);
        }
    }

    public static Connection getConnection() {
        Properties databaseProperties = new Properties();
        try {
            FileReader reader = new FileReader(Paths.get(PROPERTIES_FILE_NAME).toFile());
            databaseProperties.load(reader);
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

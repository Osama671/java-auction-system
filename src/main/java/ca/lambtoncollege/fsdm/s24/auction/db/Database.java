package ca.lambtoncollege.fsdm.s24.auction.db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private final static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(getEnv("DB_URL", "jdbc:mysql://localhost:3306/auction"));
        ds.setUsername(getEnv("DB_USERNAME", "fsdm"));
        ds.setPassword(getEnv("DB_PASSWORD", "fsdm"));
        ds.setMinIdle(2);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(20);
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Initialize() {
        try (var connection = Database.getConnection()) {
            createTableIfNotExist(connection, "User", """
                    CREATE TABLE User (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(500) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL
                    );""");

            createTableIfNotExist(connection, "Session", """
                    CREATE TABLE Session (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT,
                          session_id VARCHAR(36) NOT NULL,
                          created_at DATETIME NOT NULL,
                          INDEX (session_id),
                          INDEX (created_at),
                          FOREIGN KEY (user_id) REFERENCES User(id)
                      );""");

            createTableIfNotExist(connection, "Auction", """
                    CREATE TABLE Auction (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(500) NOT NULL,
                          description VARCHAR(3000),
                          min_bid INT NOT NULL,
                          ends_at DATETIME NOT NULL,
                          image BLOB,
                          state VARCHAR(100) NOT NULL,
                          created_by INT,
                          INDEX (title),
                          INDEX (state),
                          FOREIGN KEY (created_by) REFERENCES User(id)
                      );""");

            createTableIfNotExist(connection, "Bid", """
                    CREATE TABLE Bid (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          auction_id INT,
                          created_by INT,
                          amount INT NOT NULL,
                          INDEX (auction_id, amount),
                          FOREIGN KEY (auction_id) REFERENCES Auction(id),
                          FOREIGN KEY (created_by) REFERENCES User(id)
                      );""");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (var connection = Database.getConnection()) {
            connection.createStatement().execute("ALTER TABLE Auction MODIFY min_bid BIGINT NOT NULL");
            connection.createStatement().execute("ALTER TABLE Bid MODIFY amount BIGINT NOT NULL");
            connection.createStatement().execute("ALTER TABLE Auction MODIFY image LONGBLOB NOT NULL");
        } catch (Exception e) {
            // ignore
        }
    }

    private static void createTableIfNotExist(Connection connection, String tableName, String createQuery) throws SQLException {
        var tables = connection.getMetaData().getTables(null, null, tableName, null);

        if (!tables.next()) {
            connection.createStatement().execute(createQuery);
        }
    }

    private static String getEnv(String name, String defaultValue) {
        var env = System.getenv(name);

        if (env == null || env.isEmpty()) {
            return defaultValue;
        }

        return env;
    }

}

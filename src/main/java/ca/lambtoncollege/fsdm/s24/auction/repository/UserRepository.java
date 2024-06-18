package ca.lambtoncollege.fsdm.s24.auction.repository;

import ca.lambtoncollege.fsdm.s24.auction.db.Database;
import ca.lambtoncollege.fsdm.s24.auction.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public static void addUser(String email, String name, String password) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        INSERT INTO User (email, name, password) VALUES (?, ?, ?)
                    """);
            statement.setString(1, email);
            statement.setString(2, name);
            statement.setString(3, password);

            statement.execute();
        }
    }

    public static User findUser(String email) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        SELECT id, email, name, password
                        FROM User
                        WHERE email = ?
                    """);
            statement.setString(1, email);

            var result = statement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return fromResultSet(result);
        }
    }

    private static User fromResultSet(ResultSet result) throws SQLException {
        var user = new User();
        user.setId(result.getInt("id"));
        user.setName(result.getString("name"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));

        return user;
    }
}

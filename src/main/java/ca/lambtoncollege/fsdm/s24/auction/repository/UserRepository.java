package ca.lambtoncollege.fsdm.s24.auction.repository;

import ca.lambtoncollege.fsdm.s24.auction.db.Database;
import ca.lambtoncollege.fsdm.s24.auction.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository {
    public static void addUser(User user) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        INSERT INTO User (email, name, password) VALUES (?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            keys.next();

            user.setId(keys.getInt(1));
        }
    }

    public static User getUser(int id) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        SELECT id, email, name, password
                        FROM User
                        WHERE id = ?
                    """);
            statement.setInt(1, id);

            var result = statement.executeQuery();

            if (!result.next()) {
                return null;
            }

            return fromResultSet(result);
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

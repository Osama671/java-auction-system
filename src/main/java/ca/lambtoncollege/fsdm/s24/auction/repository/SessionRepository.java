package ca.lambtoncollege.fsdm.s24.auction.repository;

import ca.lambtoncollege.fsdm.s24.auction.db.Database;
import ca.lambtoncollege.fsdm.s24.auction.model.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.UUID;

public class SessionRepository {
    public static void addSession(Session session) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        INSERT INTO Session (user_id, session_id, created_at) VALUES (?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, session.getUser().getId());
            statement.setString(2, session.getSessionId().toString());
            statement.setTimestamp(3, Timestamp.from(session.getCreatedAt()));

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            keys.next();
            session.setId(keys.getInt(1));
        }
    }

    public static Session findSession(UUID sessionId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        SELECT * FROM Session
                        WHERE session_id = ?
                    """);
            statement.setString(1, sessionId.toString());

            var rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            var session = fromResultSet(rs);
            session.setUser(UserRepository.getUser(session.getUser().getId()));

            return session;
        }
    }

    public static void deleteSession(UUID sessionId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        DELETE FROM Session
                        WHERE session_id = ?
                    """);
            statement.setString(1, sessionId.toString());

            statement.execute();
        }
    }

    private static Session fromResultSet(ResultSet rs) throws SQLException {
        var session = new Session();

        session.setId(rs.getInt("id"));
        session.setUser(UserRepository.getUser(rs.getInt("user_id")));
        session.setSessionId(UUID.fromString(rs.getString("session_id")));
        session.setCreatedAt(rs.getTimestamp("created_at").toInstant());

        return session;
    }
}

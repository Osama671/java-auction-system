package ca.lambtoncollege.fsdm.s24.auction.repository;

import ca.lambtoncollege.fsdm.s24.auction.db.Database;
import ca.lambtoncollege.fsdm.s24.auction.model.Session;
import ca.lambtoncollege.fsdm.s24.auction.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class SessionRepository {
    public static Session createSession(User user) throws SQLException {
        UUID uuid = UUID.randomUUID();

        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        INSERT INTO Session (user_id, session_id, created_at) VALUES (?, ?, ?)
                    """);
            statement.setInt(1, user.getId());
            statement.setString(2, uuid.toString());
            statement.setTimestamp(3, Timestamp.from(Instant.now()));

            statement.execute();

            return findSession(uuid);
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

            return fromResultSet(rs);
        }
    }

    private static Session fromResultSet(ResultSet rs) throws SQLException {
        var session = new Session();

        session.setId(rs.getInt("id"));
        session.setUserId(rs.getInt("user_id"));
        session.setSessionId(UUID.fromString(rs.getString("session_id")));
        session.setCreatedAt(rs.getTimestamp("created_at").toInstant());

        return session;
    }
}

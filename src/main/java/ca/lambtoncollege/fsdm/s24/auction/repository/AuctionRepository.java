package ca.lambtoncollege.fsdm.s24.auction.repository;

import ca.lambtoncollege.fsdm.s24.auction.db.Database;
import ca.lambtoncollege.fsdm.s24.auction.model.Auction;
import ca.lambtoncollege.fsdm.s24.auction.model.Bid;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;

public class AuctionRepository {
    private static final String PLACEHOLDER_IMAGE = "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";

    public static void addAuction(Auction auction) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        INSERT INTO Auction (title, description, min_bid, ends_at, image, state, created_by)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, auction.getTitle());
            statement.setString(2, auction.getDescription());
            statement.setLong(3, auction.getMinBid());
            statement.setTimestamp(4, Timestamp.from(auction.getEndsAt()));

            byte[] decodedByte = Base64.getDecoder().decode(PLACEHOLDER_IMAGE);
            statement.setBlob(5, new SerialBlob(decodedByte));
            statement.setString(6, auction.getState().toString());
            statement.setInt(7, auction.getCreatedBy().getId());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            keys.next();

            auction.setId(keys.getInt(1));
        }
    }

    public static Auction getAuctionById(int id) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        SELECT * FROM Auction WHERE id = ?
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);

            var rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return fromResultSet(rs);
        }
    }

    public static ArrayList<Auction> getAuctions() throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        SELECT * FROM Auction
                    """);

            var rs = statement.executeQuery();
            var auctions = new ArrayList<Auction>();
            while (rs.next()) {
                auctions.add(fromResultSet(rs));
            }

            return auctions;
        }
    }

    public static ArrayList<Auction> searchAuctions(String query) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        SELECT * FROM Auction WHERE LOWER( Auction.title ) LIKE ?
                    """);
            statement.setString(1, "%" + query.toLowerCase() + "%");
            var rs = statement.executeQuery();
            var auctions = new ArrayList<Auction>();
            while (rs.next()) {
                auctions.add(fromResultSet(rs));
            }
            return auctions;
        }
    }

    public static void checkAndUpdateAuctions() throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        UPDATE Auction SET state = 'ENDED' WHERE ends_at < NOW()
                    """);
            var rs = statement.executeUpdate();
        } catch(SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private static Auction fromResultSet(ResultSet rs) throws SQLException {
        var auction = new Auction();
        auction.setId(rs.getInt("id"));
        auction.setTitle(rs.getString("title"));
        auction.setDescription(rs.getString("description"));
        auction.setMinBid(rs.getLong("min_bid"));
        auction.setEndsAt(rs.getTimestamp("ends_at").toInstant());
        auction.setCreatedBy(UserRepository.getUserById(rs.getInt("created_by")));
        auction.setState(Auction.State.fromString(rs.getString("state")));

        return auction;
    }


}

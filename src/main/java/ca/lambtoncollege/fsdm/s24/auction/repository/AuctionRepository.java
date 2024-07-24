package ca.lambtoncollege.fsdm.s24.auction.repository;

import ca.lambtoncollege.fsdm.s24.auction.db.Database;
import ca.lambtoncollege.fsdm.s24.auction.model.Auction;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;

public class AuctionRepository {

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

            if (auction.getImage() != null && !auction.getImage().isEmpty()) {
                byte[] decodedImageBytes = Base64.getDecoder().decode(auction.getImage());
                statement.setBlob(5, new SerialBlob(decodedImageBytes));
            } else {
                statement.setNull(5, java.sql.Types.BLOB);
            }
            statement.setString(6, auction.getState().toString());
            statement.setInt(7, auction.getCreatedBy().getId());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                auction.setId(keys.getInt(1));
            }
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
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }


    public static void closeAuction(int auctionId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        UPDATE Auction SET state = ? WHERE id = ?
                    """);
            statement.setString(1, Auction.State.Closed.toString());
            statement.setInt(2, auctionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void endAuctionEarly(int auctionId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        UPDATE Auction SET state = ? WHERE id = ?
                    """);
            statement.setString(1, Auction.State.EndedEarly.toString());
            statement.setInt(2, auctionId);
            statement.executeUpdate();
        }catch (SQLException e) {
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
        auction.setAuctionImage(rs.getBytes("image"));
        if (rs.getBytes("image") != null) {
            String base64Image = Base64.getEncoder().encodeToString(rs.getBytes("image"));
            auction.setImageBase64(base64Image);
        } else {
            auction.setImageBase64("");
        }
        return auction;
    }


}


package ca.lambtoncollege.fsdm.s24.auction.repository;

import ca.lambtoncollege.fsdm.s24.auction.db.Database;
import ca.lambtoncollege.fsdm.s24.auction.model.Bid;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidRepository {
    public static Bid getHighestBid(int id) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        SELECT * FROM Bid WHERE auction_id = ? ORDER BY amount DESC LIMIT 1
                    """);
            statement.setInt(1, id);
            System.out.println(statement);
            var rs = statement.executeQuery();
            if(rs.next()) {
                return bidFromResultSet(rs);
            }
            return null;
        } catch(SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void addBid(int auction_id, int created_by, int amount) throws SQLException {
        try (var connection = Database.getConnection()) {
            var statement = connection.prepareStatement("""
                        INSERT INTO Bid (auction_id, created_by, amount) VALUES (?, ?, ?)
                    """);
            statement.setInt(1, auction_id);
            statement.setInt(2, created_by);
            statement.setInt(3, amount);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private static Bid bidFromResultSet(ResultSet rs) throws SQLException {
        var bid = new Bid();
        bid.setId(rs.getInt("id"));
        bid.setCreatedBy(UserRepository.getUserById(rs.getInt("created_by")));
        bid.setAmount(rs.getInt("amount"));
        bid.setAuctionId(rs.getInt("auction_id"));
        return bid;
    }
}

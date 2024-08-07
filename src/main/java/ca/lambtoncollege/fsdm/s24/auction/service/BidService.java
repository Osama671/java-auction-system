package ca.lambtoncollege.fsdm.s24.auction.service;

import ca.lambtoncollege.fsdm.s24.auction.model.Bid;
import ca.lambtoncollege.fsdm.s24.auction.repository.BidRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BidService {
    public static ArrayList<Bid> getAllBids() throws SQLException {
        var bids = BidRepository.getBids();

        return bids;
    };

    public static ArrayList<Bid> getMyHighestBids(int userId) throws SQLException {
        var bids = BidService.getAllBids();
        bids.sort(Comparator.comparingInt(Bid::getAmount).reversed());

        Map<Integer, Bid> highestBidsMap = new HashMap<>();

        for (Bid bid : bids) {
            if (bid.getCreatedBy().getId() == userId) {
                highestBidsMap.putIfAbsent(bid.getAuctionId(), bid);
            }
        }
        ArrayList<Bid> myBids = new ArrayList<>(highestBidsMap.values());

        return myBids;
    };
}

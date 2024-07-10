package ca.lambtoncollege.fsdm.s24.auction.helper;

import ca.lambtoncollege.fsdm.s24.auction.model.Auction;

public class AuctionHelper {
    public static String getAuctionStateText(Auction.State state) {
        return switch (state) {
            case Open -> "Auction is Open";
            case Closed -> "Auction is closed";
            case Ended -> "Auction has ended";
            case EndedEarly -> "Auction ended early";
            default -> "Invalid state provided";
        };
    }
}

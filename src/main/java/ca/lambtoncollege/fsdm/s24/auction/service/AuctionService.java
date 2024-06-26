package ca.lambtoncollege.fsdm.s24.auction.service;

import ca.lambtoncollege.fsdm.s24.auction.error.ValidationException;
import ca.lambtoncollege.fsdm.s24.auction.model.Auction;
import ca.lambtoncollege.fsdm.s24.auction.model.User;
import ca.lambtoncollege.fsdm.s24.auction.repository.AuctionRepository;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class AuctionService {
    public static Auction createAuction(String title, String description, String minBid, String endDate, User user) throws ValidationException, SQLException {
        var errors = new ArrayList<String>();
        Instant endDateInstant = Instant.now();
        long minBidAmount = 0;

        if (title == null || title.isEmpty()) {
            errors.add("Title is required");
        }

        if (title != null && title.length() > 200) {
            errors.add("Title should be 200 characters or less");
        }

        if (description == null || description.isEmpty()) {
            errors.add("Description is required");
        }

        if (description != null && description.length() > 3000) {
            errors.add("Description should be 3000 characters or less");
        }

        if (minBid == null || minBid.isEmpty()) {
            errors.add("Minimum bid amount is required");
        }

        if (minBid != null && !minBid.isEmpty() && !minBid.matches("^\\d+(\\.\\d{1,2})?$")) {
            errors.add("Invalid Bid Amount. Please enter a dollar amount with optional cents (eg: 100.1, 35.99)");
        } else if (minBid != null) {
            minBidAmount = (long) (Float.parseFloat(minBid) * 100);

            if (minBidAmount < 100) {
                errors.add("Minimum Bid should be $1.00 or more");
            }
        }

        if (endDate == null || endDate.isEmpty()) {
            errors.add("Auction End Date is required");
        }

        if (endDate != null && !endDate.isEmpty() && !endDate.matches("^\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            errors.add("Invalid End Date (YYYY/MM/DD hh:mm:ss");
        } else if (endDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(endDate, formatter);
            endDateInstant = localDateTime.toInstant(ZoneOffset.UTC);
        }

        if (endDateInstant.isBefore(Instant.now())) {
            errors.add("End Date cannot be in the past");
        }

        if (endDateInstant.isAfter(Instant.now().plus(30, ChronoUnit.DAYS))) {
            errors.add("End Date cannot be later than 1 month from now");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }


        var auction = new Auction();
        auction.setTitle(title);
        auction.setDescription(description);
        auction.setMinBid(minBidAmount);
        auction.setEndsAt(endDateInstant);
        auction.setState(Auction.State.Open);
        auction.setCreatedBy(user);

        AuctionRepository.addAuction(auction);

        return auction;
    }

    public static Auction getAuction(int id) throws Exception {
        var auction = AuctionRepository.getAuctionById(id);

        if (auction == null) {
            throw new Exception("Auction with id " + id + " does not exist");
        }

        return auction;
    }
}

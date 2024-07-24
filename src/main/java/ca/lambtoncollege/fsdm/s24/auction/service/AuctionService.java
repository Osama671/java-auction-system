package ca.lambtoncollege.fsdm.s24.auction.service;

import ca.lambtoncollege.fsdm.s24.auction.error.ValidationException;
import ca.lambtoncollege.fsdm.s24.auction.model.Auction;
import ca.lambtoncollege.fsdm.s24.auction.model.Bid;
import ca.lambtoncollege.fsdm.s24.auction.model.User;
import ca.lambtoncollege.fsdm.s24.auction.repository.AuctionRepository;
import ca.lambtoncollege.fsdm.s24.auction.repository.BidRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;

public class AuctionService {
    public static Auction createAuction(String title, String description, String minBid, String endDate, Part imagePart, User user, HttpServletRequest req) throws ValidationException, SQLException, IOException {
        var errors = new ArrayList<String>();
        Instant endDateInstant = Instant.now();
        long minBidAmount = 0;
        long maxFileSize = 1024 * 1024; // 1MB
        HttpSession session = req.getSession();
        String uploadedImage = (String) session.getAttribute("uploadedImage");

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

        if (imagePart != null && imagePart.getSize() > 0) {
            if (imagePart.getSize() > maxFileSize) {
                errors.add("Image file size should be less than 1MB");
                throw new ValidationException(errors);
            }
            InputStream imageStream = imagePart.getInputStream();
            uploadedImage = Base64.getEncoder().encodeToString(imageStream.readAllBytes());
            // Store uploaded image in session
            session.setAttribute("uploadedImage", uploadedImage);
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
        if (uploadedImage != null && !uploadedImage.isEmpty()) {
            auction.setImage(uploadedImage);
        }

        AuctionRepository.addAuction(auction);
        session.removeAttribute("uploadedImage");

        return auction;
    }

    public static Auction getAuction(int id) throws Exception {
        AuctionRepository.checkAndUpdateAuctions();
        var auction = AuctionRepository.getAuctionById(id);

        if (auction == null) {
            throw new Exception("Auction with id " + id + " does not exist");
        }

        return auction;
    }
    public static Bid getHighestBid(int id) throws Exception {
        AuctionRepository.checkAndUpdateAuctions();
        return BidRepository.getHighestBid(id);
    }

    public static void addBid(int auction_id, int created_by, int amount) throws Exception {
        AuctionRepository.checkAndUpdateAuctions();
        Auction auction = AuctionRepository.getAuctionById(auction_id);

        var errors = new ArrayList<String>();
        if (auction == null) {
            errors.add("Auction with id " + auction_id + " does not exist");
        }
        if(auction.getState() != Auction.State.Open) {
            errors.add("Auction is Closed");
        }
        Bid highestBid = BidRepository.getHighestBid(auction_id);
        if( highestBid != null && amount <= highestBid.getAmount()) {
            errors.add("Bid can't be less than equal to the current highest bid");
        }
        if(auction.getCreatedBy().getId() == created_by) {
            errors.add("You cannot bid on your listing");
        }
        if(highestBid != null && highestBid.getCreatedBy().getId() == created_by) {
            errors.add("You cannot outbid yourself");
        }
        if(amount < auction.getMinBid()) {
            errors.add("You cannot bid less than the minimum bid");
        }
        if(!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        BidRepository.addBid(auction_id, created_by, amount);
    }

    public static ArrayList<Auction> getAuctions() throws Exception {
        AuctionRepository.checkAndUpdateAuctions();
        return AuctionRepository.getAuctions();
    }

    public static ArrayList<Auction> searchAuctions(String query) throws Exception {
        AuctionRepository.checkAndUpdateAuctions();
        if (query == null || query.isEmpty()) {
            var errors = new ArrayList<String>();
            errors.add("Auction search query is required");
            throw new ValidationException(errors);
        }
        return AuctionRepository.searchAuctions(query);
    }

    public static void closeAuction(int auction_id, int user_id) throws Exception {
        var auction = AuctionService.getAuction(auction_id);
        if(auction.getCreatedBy().getId() == user_id) {
            AuctionRepository.closeAuction(auction_id);
        } else {
            throw new Exception("Only user who created auction can close it");
        }
    }

    public static void endAuctionEarly(int auctionId, int userId) throws Exception {
        Auction auction = getAuction(auctionId);
        if (auction.getCreatedBy().getId() == userId && auction.getState() == Auction.State.Open) {
            AuctionRepository.endAuctionEarly(auctionId);
        } else {
            throw new Exception("Only the creator can end the auction early and the auction must be open.");
        }
    }
}

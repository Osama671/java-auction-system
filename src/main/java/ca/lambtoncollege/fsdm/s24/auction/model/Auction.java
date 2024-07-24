package ca.lambtoncollege.fsdm.s24.auction.model;

import java.time.Instant;

public class Auction {
    private int id;
    private String title;
    private String description;
    private long minBid;
    private Instant endsAt;
    private State state;
    private User createdBy;
    private String auctionImage;
    private byte[] image;
    private String imageBase64;

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Instant getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Instant endsAt) {
        this.endsAt = endsAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getMinBid() {
        return minBid;
    }

    public void setMinBid(long minBid) {
        this.minBid = minBid;
    }

    public void setImage(String image) {
        this.auctionImage = image;
    }

    public String getImage() {
        return this.auctionImage;
    }

    public byte[] getAuctionImage() {
        return image;
    }

    public void setAuctionImage(byte[] image) {
        this.image = image;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public enum State {
        Open, Closed, Ended, EndedEarly;

        public static State fromString(String state) {
            switch (state) {
                case "OPEN" -> {
                    return Open;
                }
                case "CLOSED" -> {
                    return Closed;
                }
                case "ENDED" -> {
                    return Ended;
                }
                case "ENDED_EARLY" -> {
                    return EndedEarly;
                }
                default -> throw new Error("Invalid State");
            }
        }

        public String toString() {
            switch (this) {
                case Open -> {
                    return "OPEN";
                }
                case Closed -> {
                    return "CLOSED";
                }
                case Ended -> {
                    return "Ended";
                }
                default -> {
                    return "ENDED_EARLY";
                }
            }
        }
    }
}

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

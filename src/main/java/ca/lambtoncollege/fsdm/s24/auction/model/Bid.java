package ca.lambtoncollege.fsdm.s24.auction.model;

public class Bid {
    private int id;
    private int auction_id;
    private User created_by;
    private int amount;
    public int getId() {
            return id;
        }
    public void setId(int id) {
        this.id = id;
    }
    public int getAuctionId() {
        return auction_id;
    }
    public void setAuctionId(int auction_id) {
        this.auction_id = auction_id;
    }
    public User getCreatedBy() {
        return created_by;
    }
    public void setCreatedBy(User created_by) {
        this.created_by = created_by;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}

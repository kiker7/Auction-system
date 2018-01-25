package pl.rutynar.auctionsystem.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int offer;

    @Temporal(TemporalType.DATE)
    private Date requestTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "auctionId")
    private Auction auction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private User user;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package pl.rutynar.auctionsystem.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Bids")
@Data
@NoArgsConstructor
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
}

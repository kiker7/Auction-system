package pl.rutynar.auctionsystem.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.rutynar.auctionsystem.data.Observer;
import pl.rutynar.auctionsystem.data.Subject;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Auctions")
@Data @EqualsAndHashCode(exclude = {"followers", "game"})
@ToString(exclude = {"game", "user"})
@NoArgsConstructor
public class Auction implements Subject {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date closingTime;

    @Column(nullable = false)
    private boolean finished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "auction")
    private List<Bid> bids;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_followed_auctions",
            joinColumns = @JoinColumn(name = "auction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> followers;

    @Override
    public void registerObserver(Observer observer) {
        followers.add((User) observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        followers.remove((User) observer);
    }

    @Override
    public void notifyObservers(Bid bid, Event event) {
        followers.forEach(observer -> observer.update(bid, game, event));
    }

    @Override
    public void submitNewBid(Bid bid, Event eventType) {
        notifyObservers(bid, eventType);
    }
}

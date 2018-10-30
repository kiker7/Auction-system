package pl.rutynar.auctionsystem.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.rutynar.auctionsystem.data.Observer;
import pl.rutynar.auctionsystem.data.Subject;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Auctions")
@Data @EqualsAndHashCode(exclude = {"followers", "game"})
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
    public void notifyObservers() {
        followers.forEach(observer -> observer.update(observer, game.getName(), game.getPrice()));
    }

    @Override
    public boolean submitNewBid(Bid bid) {
        if(bid.getOffer() > game.getPrice()){
            bids.add(bid);
            notifyObservers();
            return true;
        }
        return false;
    }
}

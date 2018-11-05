package pl.rutynar.auctionsystem.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.rutynar.auctionsystem.data.Observer;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data @EqualsAndHashCode(exclude = "followedAuctions")
@ToString(exclude = {"followedAuctions", "auctions"})
@NoArgsConstructor
public class User implements Observer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Library library;

    @OneToMany(mappedBy = "user")
    private List<Auction> auctions;

    @OneToMany(mappedBy = "user")
    private List<Bid> bids;

    @ManyToMany(mappedBy = "followers")
    private Set<Auction> followedAuctions;

    @OneToMany(mappedBy = "recipient")
    private List<Notification> notifications;

    @Override
    public void update(Bid bid, Game game, Event eventType) {
        switch (eventType){
            case BID:
                System.out.println("Użytkownik: " + bid.getUser().getLogin().toUpperCase() + " złożył ofertę: " + bid.getOffer() + " zł za grę " + game.getName());
                break;
            case FINISHED:
                System.out.println("Aukcja gry: " + game.getName() + " została zakończona.");
                break;
        }
    }
}

package pl.rutynar.auctionsystem.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.rutynar.auctionsystem.data.Observer;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
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
    public void update(Observer observer, String gameName, int price) {
        Notification notification = new Notification();
        notification.setRecipient((User) observer);
        notification.setEventType(Event.BID);
        notification.setMessage("Nowa oferta: " + price + " PLN za grę: " + gameName + " od użtkownika: " + ((User) observer).getLogin());
        notifications.add(notification);
    }
}

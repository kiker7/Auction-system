package pl.rutynar.auctionsystem.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Games")
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String ownerName;

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
    private Auction auction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "libraryId")
    private Library library;

}

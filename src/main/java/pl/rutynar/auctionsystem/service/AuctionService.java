package pl.rutynar.auctionsystem.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Game;
import pl.rutynar.auctionsystem.data.domain.User;


public interface AuctionService {

    Auction getAuctionById(long id);

    void createAuctionFromGame(Game game);

    Page<Auction> getAllAuctions(Pageable pageable);

    String calculateTimeLeftToCloseAuction(Auction auction);

    String checkUserPermissionToFollow(User user, Auction auction);

    void addFollower(User user, Auction auction);

    void removeFollower(User user, Auction auction);
}

package pl.rutynar.auctionsystem.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.rutynar.auctionsystem.data.domain.*;
import pl.rutynar.auctionsystem.dto.BidDTO;

import java.util.List;
import java.util.stream.Stream;


public interface AuctionService {

    Auction getAuctionById(long id);

    void createAuctionFromGame(Game game);

    Page<Auction> getAllAuctions(Pageable pageable);

    String calculateTimeLeftToCloseAuction(Auction auction);

    String checkUserPermissionToFollow(User user, Auction auction);

    void addFollower(User user, Auction auction);

    void removeFollower(User user, Auction auction);

    void closeAuction(Auction auction);

    void processNewBid(Auction auction, BidDTO newBid);

    List<Bid> getBidsForAuction(Auction auction);

    void notifyObservers(Auction auction, Event eventType, Bid bid);
}

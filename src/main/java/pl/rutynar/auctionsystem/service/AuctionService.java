package pl.rutynar.auctionsystem.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Game;


public interface AuctionService {

    Auction getAuctionById(long id);

    void createAuctionFromGame(Game game);

    Page<Auction> getAllAuctions(Pageable pageable);
}

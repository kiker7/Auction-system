package pl.rutynar.auctionsystem.service;


import pl.rutynar.auctionsystem.data.domain.Auction;

import java.util.Optional;

public interface AuctionService {

    Optional<Auction> getAuctionById(long id);
}

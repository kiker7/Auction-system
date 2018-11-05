package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Bid;

import java.util.List;

public interface BidRepository extends CrudRepository<Bid, Long> {

    List<Bid> findAllByAuctionOrderByRequestTimeDesc(Auction auction);
}

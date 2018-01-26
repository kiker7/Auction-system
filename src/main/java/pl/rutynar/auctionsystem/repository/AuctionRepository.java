package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.domain.Auction;

public interface AuctionRepository extends CrudRepository<Auction, Long> {

}

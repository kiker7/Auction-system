package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.data.domain.Auction;

import java.util.Optional;

public interface AuctionRepository extends CrudRepository<Auction, Long> {

    Optional<Auction> findOneById(long id);
}

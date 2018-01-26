package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.domain.Bid;

public interface BidRepository extends CrudRepository<Bid, Long> {
}

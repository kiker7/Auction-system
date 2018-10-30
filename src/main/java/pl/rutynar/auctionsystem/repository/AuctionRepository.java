package pl.rutynar.auctionsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.rutynar.auctionsystem.data.domain.Auction;

import java.util.Optional;

public interface AuctionRepository extends PagingAndSortingRepository<Auction, Long> {

    Optional<Auction> findOneById(long id);
    Page<Auction> findAllByOrderByClosingTimeDesc(Pageable pageable);
}

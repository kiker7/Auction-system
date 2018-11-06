package pl.rutynar.auctionsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.User;

import java.util.Optional;
import java.util.stream.Stream;

public interface AuctionRepository extends PagingAndSortingRepository<Auction, Long> {

    Optional<Auction> findOneById(long id);
    Page<Auction> findAllByAndFinishedIsFalse(Pageable pageable);
    Optional<Auction> findOneByUser(User user);
    Stream<Auction> findAllByAndFinishedIsFalse();
}

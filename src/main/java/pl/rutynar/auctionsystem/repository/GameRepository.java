package pl.rutynar.auctionsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.rutynar.auctionsystem.domain.Auction;
import pl.rutynar.auctionsystem.domain.Game;
import pl.rutynar.auctionsystem.domain.Library;

import java.util.Optional;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

    Optional<Game> findOneByName(String name);
    Optional<Game> findOneByAuction(Auction auction);
    Page<Game> findAllByLibrary( Library library, Pageable pageable);

}

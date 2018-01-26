package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.domain.Game;

public interface GameRepository extends CrudRepository<Game, Long> {


}

package pl.rutynar.auctionsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Game;
import pl.rutynar.auctionsystem.data.domain.Library;
import pl.rutynar.auctionsystem.dto.CreateGameFormDTO;

import java.util.Optional;

public interface GameService {

    Optional<Game> getGameByName(String name);

    Game getGameById(long id);

    Optional<Game> getGameByAuction(Auction auction);

    Page<Game> getAllGamesByLibrary(Pageable pageable, Library library);

    Game createGameFromForm(CreateGameFormDTO form);

    Page<Game> getAllGames(Pageable pageable);

}

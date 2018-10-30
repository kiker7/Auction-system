package pl.rutynar.auctionsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Game;
import pl.rutynar.auctionsystem.data.domain.Library;
import pl.rutynar.auctionsystem.data.domain.User;
import pl.rutynar.auctionsystem.dto.CreateGameFormDTO;
import pl.rutynar.auctionsystem.exception.GameNotFoundException;
import pl.rutynar.auctionsystem.repository.GameRepository;
import pl.rutynar.auctionsystem.repository.LibraryRepository;
import pl.rutynar.auctionsystem.service.GameService;
import pl.rutynar.auctionsystem.service.UserService;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public Optional<Game> getGameByName(String name) {
        return gameRepository.findOneByName(name);
    }

    @Override
    public Optional<Game> getGameByAuction(Auction auction) {
        return gameRepository.findOneByAuction(auction);
    }

    @Override
    public Page<Game> getAllGamesByLibrary(Pageable pageable, Library library) {
        return gameRepository.findAllByLibrary(library, pageable);
    }

    @Override
    public Game getGameById(long id) {
        return gameRepository.findOneById(id).orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public Game createGameFromForm(CreateGameFormDTO form) {

        Game game = new Game();
        game.setName(form.getName());
        game.setPrice(form.getPrice());

        User user = userService.getCurrentUser();
        // Set owner name
        game.setOwnerName(user.getLogin());

        // Save game in library
        Library library = user.getLibrary();
        library.getGames().add(game);
        libraryRepository.save(library);
        game.setLibrary(library);

        return gameRepository.save(game);
    }

    @Override
    public Page<Game> getAllGames(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }
}

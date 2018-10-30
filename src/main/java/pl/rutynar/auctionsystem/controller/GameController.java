package pl.rutynar.auctionsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Game;
import pl.rutynar.auctionsystem.dto.CreateGameFormDTO;
import pl.rutynar.auctionsystem.service.AuctionService;
import pl.rutynar.auctionsystem.service.GameService;
import pl.rutynar.auctionsystem.service.UserService;
import pl.rutynar.auctionsystem.wrapper.PageWrapper;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/game")
public class GameController {

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 6;
    private static final int[] PAGE_SIZES = {6, 12};

    @Autowired
    private GameService gameService;

    @Autowired
    private AuctionService auctionService;

    // TODO: show game details
    @GetMapping("{gameId}")
    public ModelAndView game(@PathVariable Integer gameId) {

        ModelAndView modelAndView = new ModelAndView("game/game-details");
        Game game = gameService.getGameById(gameId);
        modelAndView.addObject("game", game);

        return modelAndView;
    }


    @GetMapping("/create-auction/{gameId}")
    public String addNewAuction(@PathVariable long gameId){

        auctionService.createAuctionFromGame(gameService.getGameById(gameId));
        return "redirect:/auctions";
    }

    @GetMapping("add")
    public String getNewGameForm(@ModelAttribute("newGame") CreateGameFormDTO newGame) {
        return "game/add-game";
    }

    @PostMapping("add")
    public String addNewGame(@ModelAttribute("newGame") CreateGameFormDTO createGameFormDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "game/add-game";
        }
        gameService.createGameFromForm(createGameFormDTO);
        return "redirect:/library";
    }

    @GetMapping("all")
    public ModelAndView allGames(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page) {
        ModelAndView modelAndView = new ModelAndView("game/games");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Game> games = gameService.getAllGames(new PageRequest(evalPage, evalPageSize));
        PageWrapper pageWrapper = new PageWrapper(games.getTotalPages(), games.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("games", games);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pageWrapper);

        return modelAndView;
    }


}

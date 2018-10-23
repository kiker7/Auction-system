package pl.rutynar.auctionsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.rutynar.auctionsystem.domain.Game;
import pl.rutynar.auctionsystem.domain.User;
import pl.rutynar.auctionsystem.service.GameService;
import pl.rutynar.auctionsystem.service.UserService;
import pl.rutynar.auctionsystem.wrapper.PageWrapper;

import java.util.Optional;

@Controller
public class LibraryController {


    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 6;
    private static final int[] PAGE_SIZES = {6, 12};

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @GetMapping("/library")
    public ModelAndView library(@RequestParam("pageSize")Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page)
    {
        ModelAndView modelAndView = new ModelAndView("library");

        User user = userService.getCurrentUser();

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        // Get current user games from library
        Page<Game> gameList = gameService.getAllGamesByLibrary(new PageRequest(evalPage, evalPageSize), user.getLibrary());
        PageWrapper pageWrapper = new PageWrapper(gameList.getTotalPages(), gameList.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("gameList", gameList);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pageWrapper);

        return modelAndView;
    }
}

package pl.rutynar.auctionsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.rutynar.auctionsystem.dto.CreateGameFormDTO;
import pl.rutynar.auctionsystem.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping()
    public String game(){
        return "game/game";
    }

    @GetMapping("add")
    public String getNewGameForm(@ModelAttribute("newGame") CreateGameFormDTO newGame){
        return "game/add-game";
    }

    @PostMapping("add")
    public String addNewGame(@ModelAttribute("newGame")CreateGameFormDTO createGameFormDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()){ return "game/add-game";}

        gameService.createGameFromForm(createGameFormDTO);

        return "redirect:/library";
    }
}

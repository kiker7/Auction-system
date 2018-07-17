package pl.rutynar.auctionsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.rutynar.auctionsystem.domain.User;
import pl.rutynar.auctionsystem.service.UserService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){

        return "redirect:/home";
    }

    @GetMapping("/home")
    public String userHome(Model model){

        model.addAttribute("users", userService.getAllUsers());

        return "home";
    }
}

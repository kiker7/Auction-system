package pl.rutynar.auctionsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.rutynar.auctionsystem.dto.CreateUserFormDTO;
import pl.rutynar.auctionsystem.service.AuthenticationService;
import pl.rutynar.auctionsystem.service.UserService;
import pl.rutynar.auctionsystem.validator.CreateUserFormValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CreateUserFormValidator createUserFormValidator;

    @GetMapping
    public String getRegistrationForm(@ModelAttribute("newUser")CreateUserFormDTO newUser){
        return "register";
    }

    @PostMapping
    public String processRegistrationOfNewUser(@ModelAttribute("newUser") CreateUserFormDTO newUser, BindingResult bindingResult){

        createUserFormValidator.validate(newUser, bindingResult);
        if(bindingResult.hasErrors()){ return "register";  }

        userService.createUserFromForm(newUser);
        authenticationService.autologin(newUser.getLogin(), newUser.getPassword());

        return "redirect:/home";

    }
}

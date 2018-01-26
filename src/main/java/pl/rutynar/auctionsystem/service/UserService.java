package pl.rutynar.auctionsystem.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.rutynar.auctionsystem.domain.User;
import pl.rutynar.auctionsystem.dto.CreateUserFormDTO;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> getUserByLogin(String login);

    Optional<User> getUserByEmail(String email);;

    User getCurrentUser();

    User createUserFromForm(CreateUserFormDTO form);


}

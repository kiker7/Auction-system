package pl.rutynar.auctionsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.rutynar.auctionsystem.domain.User;
import pl.rutynar.auctionsystem.dto.CreateUserFormDTO;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> getUserByLogin(String login);

    Optional<User> getUserByEmail(String email);;

    User getCurrentUser();

    User createUserFromForm(CreateUserFormDTO form);

    Page<User> getAllUsers(Pageable pageable);
}

package pl.rutynar.auctionsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.rutynar.auctionsystem.data.domain.Game;
import pl.rutynar.auctionsystem.data.domain.Library;
import pl.rutynar.auctionsystem.data.domain.Notification;
import pl.rutynar.auctionsystem.data.domain.User;
import pl.rutynar.auctionsystem.dto.CreateUserFormDTO;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> getUserByLogin(String login);

    Optional<User> getUserByEmail(String email);;

    User getCurrentUser();

    User createUserFromForm(CreateUserFormDTO form);

    Page<User> getAllUsers(Pageable pageable);

    Page<Game> getUserGamesFromLibrary(Pageable pageable, Library library);

    List<Notification> getUserNotifications(User user);
}

package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.domain.User;

import java.util.Optional;

public interface UserRepository  extends CrudRepository<User, Long>{

    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByEmail(String email);
}

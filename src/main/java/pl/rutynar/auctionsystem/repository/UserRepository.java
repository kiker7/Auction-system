package pl.rutynar.auctionsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.rutynar.auctionsystem.data.domain.User;

import java.util.Optional;

public interface UserRepository  extends PagingAndSortingRepository<User, Long> {

    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByEmail(String email);
    Page<User> findAll(Pageable pageable);
}

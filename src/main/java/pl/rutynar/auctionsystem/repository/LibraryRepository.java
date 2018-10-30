package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.data.domain.Library;
import pl.rutynar.auctionsystem.data.domain.User;

import java.util.Optional;

public interface LibraryRepository extends CrudRepository<Library, Long> {

    Optional<Library> findOneByUser(User user);
}

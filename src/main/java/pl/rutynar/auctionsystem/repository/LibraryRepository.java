package pl.rutynar.auctionsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.domain.Game;
import pl.rutynar.auctionsystem.domain.Library;
import pl.rutynar.auctionsystem.domain.User;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;

public interface LibraryRepository extends CrudRepository<Library, Long> {

    Optional<Library> findOneByUser(User user);
}

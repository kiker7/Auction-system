package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.domain.Library;

public interface LibraryRepository extends CrudRepository<Library, Long> {
}

package pl.rutynar.auctionsystem.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rutynar.auctionsystem.data.domain.Notification;
import pl.rutynar.auctionsystem.data.domain.User;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findAllByRecipientOrderByIdDesc(User recipient);
}

package pl.rutynar.auctionsystem.data;

import pl.rutynar.auctionsystem.data.domain.Bid;
import pl.rutynar.auctionsystem.data.domain.Event;
import pl.rutynar.auctionsystem.data.domain.Game;

public interface Observer {

    void update(Bid bid, Game game, Event eventType);
}

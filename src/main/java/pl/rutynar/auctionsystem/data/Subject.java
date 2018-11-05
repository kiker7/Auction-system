package pl.rutynar.auctionsystem.data;

import pl.rutynar.auctionsystem.data.domain.Bid;
import pl.rutynar.auctionsystem.data.domain.Event;

public interface Subject {

    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Bid bid, Event event);
    void submitNewBid(Bid bid, Event eventType);
}

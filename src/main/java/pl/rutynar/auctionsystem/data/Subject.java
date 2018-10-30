package pl.rutynar.auctionsystem.data;

import pl.rutynar.auctionsystem.data.domain.Bid;

public interface Subject {

    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    boolean submitNewBid(Bid bid);
}

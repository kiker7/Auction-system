package pl.rutynar.auctionsystem.data;

public interface Observer {

    void update(Observer observer, String gameName, int price);
}

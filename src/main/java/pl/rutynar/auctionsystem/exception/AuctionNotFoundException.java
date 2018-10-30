package pl.rutynar.auctionsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuctionNotFoundException extends RuntimeException {

    public AuctionNotFoundException(long id) {
        super(String.format("Nie znaleziono aukcji o podanym id: %d", id));
    }
}

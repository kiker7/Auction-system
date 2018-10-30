package pl.rutynar.auctionsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(long id){
        super(String.format("Nie znaleziono gry o podanym ID: %d", id));
    }
}

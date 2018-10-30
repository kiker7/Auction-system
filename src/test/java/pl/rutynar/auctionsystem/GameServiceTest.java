package pl.rutynar.auctionsystem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.rutynar.auctionsystem.data.domain.Game;
import pl.rutynar.auctionsystem.dto.CreateGameFormDTO;
import pl.rutynar.auctionsystem.exception.GameNotFoundException;
import pl.rutynar.auctionsystem.repository.GameRepository;
import pl.rutynar.auctionsystem.service.impl.GameServiceImpl;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    private CreateGameFormDTO gameFormDTO;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    @Before
    public void setup() {
        gameFormDTO = new CreateGameFormDTO();
        gameFormDTO.setName("Test Game");
        gameFormDTO.setPrice(235);

    }

    @Test
    public void findGameTest() {
        when(gameRepository.findOneById(1)).thenReturn(Optional.of(new Game()));
        assertNotNull(gameService.getGameById(1));
    }

    @Test
    public void findGameWithExceptionThrowTest() {
        when(gameRepository.findOneById(1)).thenThrow(GameNotFoundException.class);
        exception.expect(GameNotFoundException.class);
        gameService.getGameById(1);
    }
}

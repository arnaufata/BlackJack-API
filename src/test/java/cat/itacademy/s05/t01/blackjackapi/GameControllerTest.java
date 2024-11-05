package cat.itacademy.s05.t01.blackjackapi;

import cat.itacademy.s05.t01.blackjackapi.controller.GameController;
import cat.itacademy.s05.t01.blackjackapi.exception.GameNotFoundException;
import cat.itacademy.s05.t01.blackjackapi.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import cat.itacademy.s05.t01.blackjackapi.service.GameService;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicialitza els mocks
    }

    @Test
    void testCreateGame_Success() {
        Game game = new Game(); // Crear un objecte Game de prova

        // Simular el comportament del servei
        when(gameService.createNewGame(anyLong(), anyDouble())).thenReturn(Mono.just(game));

        Mono<ResponseEntity<Game>> result = gameController.createGame(1L, 100.0);

        // Verificar amb StepVerifier
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.CREATED)
                        && Objects.equals(response.getBody(), game))
                .verifyComplete();

        verify(gameService, times(1)).createNewGame(1L, 100.0);
    }

    @Test
    void testGetGameById_Success() {
        Game game = new Game();
        game.setId("1");

        // Simular el comportament del servei
        when(gameService.getGameById(anyString())).thenReturn(Mono.just(game));

        Mono<ResponseEntity<Game>> result = gameController.getGameById("1");

        // Verificar amb StepVerifier
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.OK)
                        && Objects.equals(response.getBody(), game))
                .verifyComplete();

        verify(gameService, times(1)).getGameById("1");
    }

    @Test
    void testGetGameById_NotFound() {
        // Simulem que no es troba la partida (GameNotFoundException)
        when(gameService.getGameById(anyString())).thenReturn(Mono.error(new GameNotFoundException("1")));

        Mono<ResponseEntity<Game>> result = gameController.getGameById("1");

        // Verificar amb StepVerifier
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof GameNotFoundException &&
                        throwable.getMessage().equals("Partida no trobada amb id: 1"))
                .verify();  // Verifica que l'error es gestiona correctament

        verify(gameService, times(1)).getGameById("1");
    }


    @Test
    void testPlayTurn_Success() {
        Game game = new Game();
        game.setStatus("IN_PROGRESS");

        // Simular el servei
        when(gameService.playTurn(anyString(), anyBoolean())).thenReturn(Mono.just(game));

        Mono<ResponseEntity<Game>> result = gameController.playTurn("1", true);

        // Verificar amb StepVerifier
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().equals(HttpStatus.OK)
                        && Objects.equals(response.getBody(), game))
                .verifyComplete();

        verify(gameService, times(1)).playTurn("1", true);
    }

    @Test
    void testDeleteGame_Success() {
        // Simular el servei
        when(gameService.deleteGame(anyString())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = gameController.deleteGame("1");

        // Verificar amb StepVerifier
        StepVerifier.create(result)
                .expectComplete() // Verifiquem que el Mono es completa correctament
                .verify();

        // Verificar que el servei s'ha cridat correctament
        verify(gameService, times(1)).deleteGame("1");
    }


}

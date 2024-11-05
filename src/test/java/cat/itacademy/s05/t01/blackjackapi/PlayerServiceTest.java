package cat.itacademy.s05.t01.blackjackapi;

import cat.itacademy.s05.t01.blackjackapi.exception.DatabaseException;
import cat.itacademy.s05.t01.blackjackapi.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import cat.itacademy.s05.t01.blackjackapi.repository.PlayerRepository;
import cat.itacademy.s05.t01.blackjackapi.service.PlayerService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicialitza els mocks
    }

    @Test
    void testCreatePlayer() {
        Player player = new Player("Edgar");
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

        Mono<Player> result = playerService.createPlayer("Edgar");

        // Verificar el flux reactiu amb StepVerifier
        StepVerifier.create(result)
                .expectNext(player) // Esperem el jugador creat
                .verifyComplete();  // Comprovem que el Mono acaba correctament

        // Verificar que es va cridar el mètode de save al repositori
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testUpdatePlayerName_Success() {
        // Donar dades de prova
        Player player = new Player("Edgar");
        player.setId(7L);

        // Simulem el retorn de getPlayerById i save
        when(playerRepository.findById(7L)).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

        // Provar l'actualització del nom
        Mono<Player> result = playerService.updatePlayerName(7L, "Updated Name");

        // Verificar amb StepVerifier
        StepVerifier.create(result)
                .expectNextMatches(updatedPlayer -> updatedPlayer.getName().equals("Updated Name")) // Verifiquem que el nom ha estat actualitzat
                .verifyComplete();

        // Verifiquem que getPlayerById i save han estat cridats
        verify(playerRepository, times(1)).findById(7L);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testUpdatePlayerName_PlayerNotFound() {
        // Simulem que no es troba el jugador
        when(playerRepository.findById(anyLong())).thenReturn(Mono.empty());

        // Intentar actualitzar el nom d'un jugador inexistent
        Mono<Player> result = playerService.updatePlayerName(7L, "Updated Name");

        // Verifiquem que retorna un error quan el jugador no es troba
        StepVerifier.create(result)
                .expectErrorMessage("Jugador no trobat amb id: 7")  // Excepció llançada al getPlayerById
                .verify();

        // Verifiquem que getPlayerById ha estat cridat, però no save
        verify(playerRepository, times(1)).findById(7L);
        verify(playerRepository, times(0)).save(any(Player.class));
    }

    @Test
    void testUpdatePlayerName_DatabaseException() {
        // Donar dades de prova
        Player player = new Player("Edgar");
        player.setId(7L);

        // Simulem que es troba el jugador però falla el save
        when(playerRepository.findById(7L)).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Provar l'actualització del nom i simular error en el save
        Mono<Player> result = playerService.updatePlayerName(7L, "Updated Name");

        // Verifiquem que retorna l'excepció personalitzada DatabaseException
        StepVerifier.create(result)
                .expectError(DatabaseException.class) // Verifiquem que es propaga l'excepció DatabaseException
                .verify();

        // Verifiquem que es va cridar findById i save, però el save ha fallat
        verify(playerRepository, times(1)).findById(7L);
        verify(playerRepository, times(1)).save(any(Player.class));
    }
}

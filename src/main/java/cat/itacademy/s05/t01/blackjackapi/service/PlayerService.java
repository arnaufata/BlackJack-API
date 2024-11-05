package cat.itacademy.s05.t01.blackjackapi.service;

import cat.itacademy.s05.t01.blackjackapi.exception.DatabaseException;
import cat.itacademy.s05.t01.blackjackapi.exception.PlayerNotFoundException;
import cat.itacademy.s05.t01.blackjackapi.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import cat.itacademy.s05.t01.blackjackapi.repository.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Mono<Player> createPlayer(String nom) {
        Player player = new Player(nom);
        return playerRepository.save(player)
                .onErrorResume(e -> Mono.error(new DatabaseException("No s'ha pogut crear el jugador")));
    }

    public Mono<Player> getPlayerById(Long id) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(id)));
    }


    public Mono<Player> updatePlayerName(Long id, String newName) {
        return getPlayerById(id)
                .flatMap(player -> {
                    player.setName(newName);
                    return playerRepository.save(player)
                            .onErrorResume(e -> Mono.error(new DatabaseException("No s'ha pogut actualitzar el nom del jugador")));
                });
    }

    public Flux<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Mono<Void> deletePlayer(Long id) {
        return getPlayerById(id)
                .flatMap(playerRepository::delete);
    }

    public Mono<Void> updatePlayerStats(Player player, boolean playerWin) {
        player.incrementGames();
        if (playerWin) {
            player.incrementVictories();
        } else {
            player.incrementDefeats();
        }
        return playerRepository.save(player).then();
    }
}
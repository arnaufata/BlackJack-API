package cat.itacademy.s05.t01.blackjackapi.service;

import cat.itacademy.s05.t01.blackjackapi.exception.DatabaseException;
import cat.itacademy.s05.t01.blackjackapi.exception.GameNotFoundException;
import cat.itacademy.s05.t01.blackjackapi.exception.InvalidGameActionException;
import cat.itacademy.s05.t01.blackjackapi.model.Deck;
import cat.itacademy.s05.t01.blackjackapi.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import cat.itacademy.s05.t01.blackjackapi.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;

    public Mono<Game> createNewGame(Long playerId, double bet) {
        return playerService.getPlayerById(playerId)
                .flatMap(player -> {
                    Game game = new Game(player, bet);

                    // Crea una nova baralla i mescla les cartes
                    Deck deck = new Deck();
                    deck.mesclar();

                    // Afegeix dues cartes a la mà del jugador
                    game.addCardToPlayerHand(deck.robarCarta());
                    game.addCardToPlayerHand(deck.robarCarta());

                    // Afegeix dues cartes a la mà del dealer
                    game.addCardToDealerHand(deck.robarCarta());
                    game.addCardToDealerHand(deck.robarCarta());

                    // Assigna la baralla actualitzada a la partida
                    game.setDeck(deck);
                    game.setStatus("IN_PROGRESS");

                    return gameRepository.save(game)
                            .onErrorResume(e -> Mono.error(new DatabaseException("No s'ha pogut crear la partida")));
                });
    }

    public Mono<Game> getGameById(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException(id)));
    }

    public Mono<Game> playTurn(String gameId, boolean hit) {
        return getGameById(gameId)
                .flatMap(game -> {
                    if (!game.getStatus().equals("IN_PROGRESS")) {
                        return Mono.error(new InvalidGameActionException("La partida ja ha finalitzat amb l'estat: " + game.getStatus()));
                    }

                    // Jugada del jugador
                    if (hit) {
                        game.getPlayerHand().addCard(game.getDeck().robarCarta());
                        if (game.getPlayerHand().isBusted()) {
                            game.setStatus("DEALER_WINS");
                        }
                    } else {
                        // Jugada del crupier
                        while (game.getDealerHand().getTotalValue() < 17) {
                            game.getDealerHand().addCard(game.getDeck().robarCarta());
                        }

                        // Comprovació del resultat
                        int playerTotal = game.getPlayerHand().getTotalValue();
                        int dealerTotal = game.getDealerHand().getTotalValue();

                        if (game.getDealerHand().isBusted() || playerTotal > dealerTotal) {
                            game.setStatus("PLAYER_WINS");
                        } else if (playerTotal < dealerTotal) {
                            game.setStatus("DEALER_WINS");
                        } else {
                            game.setStatus("DRAW");
                        }
                    }

                    // Comprovem si la partida ha finalitzat per actualitzar les estadístiques
                    if (!game.getStatus().equals("IN_PROGRESS")) {
                        return playerService.getPlayerById(game.getPlayer().getId())
                                .flatMap(player -> {
                                    // Utilitzem `updatePlayerStats` amb true si el jugador guanya, false si empata o perd
                                    boolean playerWin = "PLAYER_WINS".equals(game.getStatus());
                                    return playerService.updatePlayerStats(player, playerWin);
                                }).then(gameRepository.save(game))
                                .onErrorResume(e -> Mono.error(new DatabaseException("No s'ha pogut actualitzar la partida")));
                    }

                    // Si la partida continua, només la desem sense actualitzar estadístiques
                    return gameRepository.save(game);
                });
    }

    public Mono<Void> deleteGame(String id) {
        return getGameById(id)
                .flatMap(gameRepository::delete)
                .onErrorResume(e -> Mono.error(new DatabaseException("No s'ha pogut eliminar la partida")));
    }
}
package cat.itacademy.s05.t01.blackjackapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import cat.itacademy.s05.t01.blackjackapi.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import cat.itacademy.s05.t01.blackjackapi.service.GameService;

@RestController
@RequestMapping("/game")
@Tag(name = "Game Controller", description = "Controlador per gestionar les partides de BlackJack")
public class GameController {

    @Autowired
    private GameService gameService;


    @Operation(summary = "Crea una nova partida", description = "Crea una partida nova amb l'identificador del jugador i l'aposta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Partida creada correctament"),
            @ApiResponse(responseCode = "400", description = "Error en crear la partida")
    })
    @PostMapping("/new")
    public Mono<ResponseEntity<Game>> createGame (@RequestParam Long playerId, @RequestParam double bet) {
        return gameService.createNewGame(playerId, bet)
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }


    @Operation(summary = "Obtenir partida per ID", description = "Obté la informació d'una partida mitjançant el seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partida trobada correctament"),
            @ApiResponse(responseCode = "404", description = "Partida no trobada")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Game>> getGameById (@PathVariable String id) {
        return gameService.getGameById(id)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Jugar un torn", description = "Permet jugar un torn a una partida existent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Torn jugat correctament"),
            @ApiResponse(responseCode = "400", description = "Error en jugar el torn")
    })
    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<Game>> playTurn (@PathVariable String id, @RequestParam boolean hit) {
        return gameService.playTurn(id,hit)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Eliminar una partida", description = "Elimina una partida mitjançant el seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Partida eliminada correctament"),
            @ApiResponse(responseCode = "404", description = "Partida no trobada")
    })
    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Void>> deleteGame (
            @Parameter(description = "ID de la partida") @PathVariable String id) {
        return gameService.deleteGame(id)
                .map(r -> ResponseEntity.noContent().<Void>build());
    }
}
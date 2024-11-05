package cat.itacademy.s05.t01.blackjackapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import cat.itacademy.s05.t01.blackjackapi.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import cat.itacademy.s05.t01.blackjackapi.service.PlayerService;

import java.util.Map;

@RestController
@RequestMapping("/player")
@Tag(name = "Player Controller", description = "Controlador per gestionar els jugadors")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Operation(summary = "Crear un nou jugador", description = "Crea un nou jugador amb el nom subministrat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Jugador creat correctament"),
            @ApiResponse(responseCode = "400", description = "Error en crear el jugador")
    })
    @PostMapping("/add")
    public Mono<ResponseEntity<Player>> createPlayer(
            @Parameter(description = "Nou jugador") @RequestBody Player player) {
        return playerService.createPlayer(player.getName())
                .map(p -> ResponseEntity.status(201).body(p));
    }

    @Operation(summary = "Actualitzar nom del jugador", description = "Actualitza el nom d'un jugador mitjançant el seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nom del jugador actualitzat correctament"),
            @ApiResponse(responseCode = "404", description = "Jugador no trobat"),
            @ApiResponse(responseCode = "400", description = "Error en actualitzar el nom del jugador")
    })
    @PutMapping("/{playerId}")
    public Mono<ResponseEntity<Player>> updatePlayerName (
            @Parameter(description = "ID del jugador") @PathVariable Long playerId,
            @RequestBody String newName) {

        newName = newName.replaceAll("^\"|\"$", "");

        return playerService.updatePlayerName(playerId, newName)
                .map(ResponseEntity::ok);
    }
}
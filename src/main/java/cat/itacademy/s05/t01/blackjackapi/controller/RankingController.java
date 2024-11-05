package cat.itacademy.s05.t01.blackjackapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import cat.itacademy.s05.t01.blackjackapi.model.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import cat.itacademy.s05.t01.blackjackapi.service.RankingService;

@RestController
@RequestMapping("/ranking")
@Tag(name = "Ranking Controller", description = "Controlador per gestionar el ranking dels jugadors")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @Operation(summary = "Obtenir el ranking de tots els jugadors", description = "Retorna el ranking ordenat dels jugadors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking obtingut correctament")
    })
    @GetMapping
    public Flux<Ranking> getAllRankings() {

        return rankingService.getAllRankings();
    }
}
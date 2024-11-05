package cat.itacademy.s05.t01.blackjackapi.service;

import cat.itacademy.s05.t01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.blackjackapi.model.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import cat.itacademy.s05.t01.blackjackapi.repository.RankingRepository;

import java.util.Comparator;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private PlayerService playerService;

    public Mono<Ranking> getRankingForPlayer(Long playerId) {
        return rankingRepository.findById(playerId)
                .switchIfEmpty(playerService.getPlayerById(playerId)
                        .flatMap(player -> rankingRepository.save(new Ranking(player.getId(), calcularPuntuacio(player))))
                );
    }

    public Flux<Ranking> getAllRankings() {
        return rankingRepository.findAll()
                .sort(Comparator.comparing(Ranking::getScore).reversed());
    }

    public Mono<Void> updateRanking(Player player) {
        return getRankingForPlayer(player.getId())
                .flatMap(ranking -> {
                    ranking.setScore(calcularPuntuacio(player));
                    return rankingRepository.save(ranking);
                })
                .then();
    }

    private int calcularPuntuacio(Player player) {
        return (player.getTotalVictories() * 3) - player.getTotalDefeats();
    }
}
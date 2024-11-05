package cat.itacademy.s05.t01.blackjackapi.repository;

import cat.itacademy.s05.t01.blackjackapi.model.Ranking;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends R2dbcRepository<Ranking, Long> {
}
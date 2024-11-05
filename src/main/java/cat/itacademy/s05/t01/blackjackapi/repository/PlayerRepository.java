package cat.itacademy.s05.t01.blackjackapi.repository;

import cat.itacademy.s05.t01.blackjackapi.model.Player;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, Long> {
}
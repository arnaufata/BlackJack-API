package cat.itacademy.s05.t01.blackjackapi.repository;

import cat.itacademy.s05.t01.blackjackapi.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends ReactiveMongoRepository<Game, String> {
}
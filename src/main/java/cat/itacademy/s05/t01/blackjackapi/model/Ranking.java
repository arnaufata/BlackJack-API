package cat.itacademy.s05.t01.blackjackapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("rankings")
public class Ranking {

    @Id
    private long id;

    private long playerId;

    private int score;

    public Ranking() {}

    public Ranking(long playerId, int score) {
        this.playerId = playerId;
        this.score    = score;
    }

    // Getters i setters

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getPlayerId() {
        return playerId;
    }
    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
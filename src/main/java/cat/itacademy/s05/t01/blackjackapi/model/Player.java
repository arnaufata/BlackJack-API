package cat.itacademy.s05.t01.blackjackapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("players")  // Mapeja la classe a la taula players a la base de dades
public class Player {

    @Id
    private long id;

    private String name;
    private int totalVictories;
    private int totalDefeats;
    private int totalGames;

    public Player() {}

    public Player(String name) {
        this.name = name;
        this.totalVictories = 0;
        this.totalDefeats = 0;
        this.totalGames = 0;
    }

    // Getters i setters

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getTotalVictories() {
        return totalVictories;
    }
    public void setTotalVictories(int totalVictories) {
        this.totalVictories = totalVictories;
    }

    public int getTotalDefeats() {
        return totalDefeats;
    }
    public void setTotalDefeats(int totalDefeats) {
        this.totalDefeats = totalDefeats;
    }

    public int getTotalGames() {
        return totalGames;
    }
    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public void incrementVictories() {
        this.totalVictories++;
    }
    public void incrementDefeats() {
        this.totalDefeats++;
    }
    public void incrementGames() {
        this.totalGames++;
    }
}
package cat.itacademy.s05.t01.blackjackapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class MySQLconfig {

    private final DatabaseClient databaseClient;

    public MySQLconfig(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    // Executa la creació de les taules quan l'aplicació s'inicia
    @PostConstruct
    public void initializeDatabase() {
        // Creació de la taula players si no existeix
        databaseClient.sql("""
            CREATE TABLE IF NOT EXISTS players (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                total_victories INT DEFAULT 0,
                total_defeats INT DEFAULT 0,
                total_games INT DEFAULT 0
            );
        """).then()
                .doOnSuccess(unused -> System.out.println("Taula 'players' creada (si no existia)."))
                .subscribe();

        // Creació de la taula rankings si no existeix
        databaseClient.sql("""
            CREATE TABLE IF NOT EXISTS rankings (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                player_id BIGINT NOT NULL,
                score INT DEFAULT 0,
                CONSTRAINT fk_player FOREIGN KEY (player_id) REFERENCES players(id)
            );
        """).then()
                .doOnSuccess(unused -> System.out.println("Taula 'rankings' creada (si no existia)."))
                .subscribe();
    }
}
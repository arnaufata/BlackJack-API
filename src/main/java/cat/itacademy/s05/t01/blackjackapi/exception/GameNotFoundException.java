package cat.itacademy.s05.t01.blackjackapi.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException (String id) {
        super("Partida no trobada amb id: " + id);
    }
}
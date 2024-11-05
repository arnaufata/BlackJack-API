package cat.itacademy.s05.t01.blackjackapi.exception;

public class InvalidGameActionException extends RuntimeException {
    public InvalidGameActionException (String message) {
        super("Acció no vàlida en la partida: " + message);
    }
}

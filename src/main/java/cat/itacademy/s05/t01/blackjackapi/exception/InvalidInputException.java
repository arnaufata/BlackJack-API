package cat.itacademy.s05.t01.blackjackapi.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException (String message) {
        super("Entrada no vàlida: " + message);
    }
}

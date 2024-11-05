package cat.itacademy.s05.t01.blackjackapi.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super("Error en la base de dades: " + message);
    }
}
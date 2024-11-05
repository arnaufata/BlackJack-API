package cat.itacademy.s05.t01.blackjackapi.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Long id) {
        super("Jugador no trobat amb id: " + id);
    }
}

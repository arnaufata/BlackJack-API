package cat.itacademy.s05.t01.blackjackapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handlePlayerNotFound(PlayerNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("Content-Type", "application/json")
                .body(new ErrorResponse(ex.getMessage())));
    }

    @ExceptionHandler(GameNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGameNotFound(GameNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("Content-Type", "application/json")
                .body(new ErrorResponse(ex.getMessage())));
    }

    @ExceptionHandler(InvalidGameActionException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidGameAction(InvalidGameActionException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(new ErrorResponse(ex.getMessage())));
    }

    @ExceptionHandler(DatabaseException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDatabaseException(DatabaseException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(new ErrorResponse(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneralException(Exception ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(new ErrorResponse("Error intern del servidor")));
    }

    // Classe interna per encapsular les respostes d'error
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /*@ExceptionHandler(PlayerNotFoundException.class)
    public Mono<ResponseEntity<String>> handlePlayerNotFound(PlayerNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    @ExceptionHandler(GameNotFoundException.class)
    public Mono<ResponseEntity<String>> handleGameNotFound(GameNotFoundException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    @ExceptionHandler(InvalidGameActionException.class)
    public Mono<ResponseEntity<String>> handleInvalidGameAction(InvalidGameActionException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(DatabaseException.class)
    public Mono<ResponseEntity<String>> handleDatabaseException(DatabaseException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public Mono<ResponseEntity<String>> handleInvalidInput(InvalidInputException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleGeneralException(Exception ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error intern del servidor"));
    }*/
}
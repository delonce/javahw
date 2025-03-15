package app.exceptions;

public class NoAuthorizationException extends RuntimeException {

    public NoAuthorizationException(String message) {
        super(message);
    }
}

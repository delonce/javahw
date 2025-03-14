package app.exceptions;

public class GradeIsNotActiveException extends RuntimeException {

    public GradeIsNotActiveException(String message) {
        super(message);
    }
}

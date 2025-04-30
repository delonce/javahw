package bus_app.exceptions;

/**
 * Исключение, выбрасываемое при несовпадении передаваемых на сервер данных с необходимым форматом
 */
public class IncorrectBodyException extends RuntimeException {

    public IncorrectBodyException(String msg) {
        super(msg);
    }
}
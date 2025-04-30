package bus_app.exceptions;

/**
 * Исключение, выбрасываемое при невозможности найти информацию об объекте по переданному ключу
 */
public class NoDataException extends RuntimeException {

    public NoDataException(String msg) {
        super(msg);
    }
}
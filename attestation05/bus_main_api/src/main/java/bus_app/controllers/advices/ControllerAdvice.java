package bus_app.controllers.advices;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Класс контроллеров, отлавливающих выброс исключения во время обработки запросов и возвращающих код ошибки
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Метод возвращения ошибки NOT_FOUND при выбросе исключения с ошибкой доступа
     * @param ex объект выброшенного исключения
     * @return код ошибки NOT_FOUND и описание ошибки
     */
    @ExceptionHandler(NoDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoData(NoDataException ex) {
        return ex.getMessage();
    }

    /**
     * Метод возвращения ошибки DAB_REQUEST при выбросе исключения с ошибкой обработки данных
     * @param ex объект выброшенного исключения
     * @return код ошибки DAB_REQUEST и описание ошибки
     */
    @ExceptionHandler(IncorrectBodyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNoData(IncorrectBodyException ex) {
        return ex.getMessage();
    }
}
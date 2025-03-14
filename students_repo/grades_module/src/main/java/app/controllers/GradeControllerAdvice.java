package app.controllers;

import app.exceptions.NoDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GradeControllerAdvice {

    @ExceptionHandler({NoDataException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoData(NoDataException ex) {
        return ex.getMessage();
    }
}

package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNull(final NullPointerException e) {
        return Map.of("errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(final NotFoundByIdException e) {
        return Map.of("errorMessage", e.getMessage());
    }
}

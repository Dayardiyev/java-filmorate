package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EmptyResultDataAccessException.class, NotFoundByIdException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(Exception e) {
        log.warn(e.getMessage());
        return Map.of("errorMessage", e.getMessage());
    }
}

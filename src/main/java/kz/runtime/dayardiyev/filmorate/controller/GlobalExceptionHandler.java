package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    // Обработка исключения NotFoundByIdException
    // Возвращает Map с ключом "errorMessage" и значением сообщения об ошибке
    // Пример: {"errorMessage": "Фильм с id 1 не найден"}
    public Map<String, String> handleNotFound(NotFoundByIdException e) {
        log.warn(e.getMessage());
        return Map.of("errorMessage", e.getMessage());
    }
}

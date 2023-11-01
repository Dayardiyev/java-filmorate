package kz.runtime.dayardiyev.filmorate.controller;


import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private final FilmService filmService = new FilmService();

    @PostMapping
    public ResponseEntity<Object> createFilm(@RequestBody Film film) {
        try {
            Film validatedFilm = filmService.validate(film);
            films.add(validatedFilm);
            log.info("Фильм успешно был добавлен");
            return ResponseEntity.status(HttpStatus.CREATED).body(validatedFilm);
        } catch (FilmValidateException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(film);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateFilm(@RequestBody Film film) {
        try {
            Film validatedFilm = filmService.validate(film);
            filmService.update(films, film);
            log.info("Фильм успешно был обновлен");
            return ResponseEntity.status(HttpStatus.OK).body(validatedFilm);
        } catch (FilmValidateException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(film);
        }
    }

    @GetMapping
    public List<Film> findAll() {
        log.info("Просмотрен список фильмов");
        return films;
    }
}

//{
//    "id": 1,
//    "name": "TestFilm",
//    "description": "",
//    "releaseDate": "2022-02-02",
//    "duration": "PT3H10M"
//}

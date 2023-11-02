package kz.runtime.dayardiyev.filmorate.controller;


import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.service.FilmService;
import lombok.extern.slf4j.Slf4j;
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
    public Film createFilm(@RequestBody Film film) {
        Film validatedFilm = filmService.validate(film);
        films.add(validatedFilm);
        log.info("Фильм успешно был добавлен");
        return validatedFilm;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        Film validatedFilm = filmService.validate(film);
        filmService.update(films, film);
        log.info("Фильм успешно был обновлен");
        return validatedFilm;
    }

    @GetMapping
    public List<Film> findAll() {
        log.info("Просмотрен список фильмов");
        return films;
    }
}
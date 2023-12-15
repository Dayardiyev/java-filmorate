package kz.runtime.dayardiyev.filmorate.controller;


import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    @PostMapping
    public Film create(@RequestBody Film film) {
        log.info("POST / film / {}", film.getName());
        service.create(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        log.info("PUT / film / {}", film.getName());
        service.update(film);
        return film;
    }

    @GetMapping
    public List<Film> findAllFilms() {
        log.info("GET / films");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable("id") int id) {
        log.info("GET / {}", id);
        return service.findById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addFilmLike(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        log.info("PUT / {} / like / {}", id, userId);
        service.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeFilmLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        log.info("DELETE / {} / like / {}", id, userId);
        service.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> findPopular(@RequestParam(defaultValue = "10") @Positive int count) {
        log.info("GET / popular");
        return service.findAllPopular(count);
    }
}
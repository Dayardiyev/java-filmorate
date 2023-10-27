package kz.runtime.dayardiyev.filmorate.controller;


import kz.runtime.dayardiyev.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private List<Film> films = new ArrayList<>();

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return film;
    }

    @GetMapping
    public List<Film> findAll() {
        return films;
    }
}

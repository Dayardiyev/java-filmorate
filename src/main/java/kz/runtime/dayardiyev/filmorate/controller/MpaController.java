package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.model.Mpa;
import kz.runtime.dayardiyev.filmorate.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final FilmService filmService;

    @GetMapping
    public List<Mpa> findAllMpa() {
        log.info("GET / mpa");
        return filmService.findAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa findMpaById(@PathVariable("id") int id) {
        log.info("GET / mpa / {}", id);
        return filmService.findMpaById(id);
    }
}

package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.model.Genre;
import kz.runtime.dayardiyev.filmorate.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService service;


    @GetMapping
    public List<Genre> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Genre findById(@PathVariable int id) {
        return service.findById(id);
    }
}

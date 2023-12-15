package kz.runtime.dayardiyev.filmorate.service;


import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.Genre;
import kz.runtime.dayardiyev.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public List<Genre> findAll(){
        return genreStorage.findAll();
    }

    public Genre findById(int id) {
        return genreStorage.findById(id).orElseThrow(() -> new NotFoundByIdException("Genre not found by id " + id));
    }
}

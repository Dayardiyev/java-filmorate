package kz.runtime.dayardiyev.filmorate.storage;

import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage extends Storage<Genre> {
    void findAllGenresByFilm(List<Film> films);
}

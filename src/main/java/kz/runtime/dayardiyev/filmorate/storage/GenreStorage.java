package kz.runtime.dayardiyev.filmorate.storage;

import kz.runtime.dayardiyev.filmorate.model.Genre;

import java.util.Set;

public interface GenreStorage extends Storage<Genre> {
    Set<Genre> findAllGenresByFilm(int id);
}

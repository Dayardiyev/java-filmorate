package kz.runtime.dayardiyev.filmorate.storage;


import kz.runtime.dayardiyev.filmorate.model.Film;

import java.util.List;

public interface FilmStorage extends Storage<Film> {
    Film create(Film film);

    Film update(Film film);

    List<Film> findAllPopular(int count);
}

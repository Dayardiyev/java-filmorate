package kz.runtime.dayardiyev.filmorate.storage;


import kz.runtime.dayardiyev.filmorate.model.Film;

import java.util.Set;

public interface FilmStorage extends Storage<Film> {
    void addLike(long id, long userId);
    void removeLike(long id, long userId);
    Set<Film> getFilmsByCount(Integer count);
}

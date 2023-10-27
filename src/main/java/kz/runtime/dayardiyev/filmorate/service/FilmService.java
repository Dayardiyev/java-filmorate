package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.model.Film;

import java.time.LocalDate;

public class FilmService {
    public Film validate(Film film) throws FilmValidateException {
        if (isValidName(film)
                && isValidDescription(film)
                && isValidReleaseDate(film)
                && isValidDuration(film)) {
            return film;
        }
        throw new FilmValidateException("Фильм не прошел проверку, проверьте значения!");
    }

    private boolean isValidName(Film film) {
        return !(film.getName().isBlank());
    }

    private boolean isValidDescription(Film film) {
        return film.getDescription().length() <= 200;
    }

    private boolean isValidReleaseDate(Film film) {
        if (film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
            return true;
        }
        throw new RuntimeException("Дата релиза фильма не должна быть раньше 28 декабря 1895 года\n" +
                "Ваша дата: " + film.getReleaseDate());
    }

    private boolean isValidDuration(Film film) {
        if (!(film.getDuration().isNegative())){

        }
        return false;
    }
}

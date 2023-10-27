package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FilmService {

    public void update(List<Film> films, Film target) {
        Optional<Film> optionalFilm = films.stream()
                .filter(film -> film.getId().equals(target.getId()))
                .findFirst();

        if (optionalFilm.isPresent()) {
            int index = films.indexOf(optionalFilm.get());
            films.set(index, target);
        } else {
            films.add(target);
        }
    }

    public Film validate(Film film) {
        validateName(film);
        validateDescription(film);
        validateReleaseDate(film);
        validateDuration(film);
        return film;
    }

    private void validateName(Film film) {
        if (film.getName().isBlank()) {
            throw new FilmValidateException("Название фильма не должно быть пустым!");
        }
    }

    private void validateDescription(Film film) {
        int descriptionLength = film.getDescription().length();
        if (descriptionLength > 200) {
            throw new FilmValidateException("Максимальное количество символов для описания фильма: 200\n" +
                    "Количество символов в вашем описании: " + descriptionLength);
        }
    }

    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmValidateException("Дата релиза фильма не должна быть раньше 28 декабря 1895 года\n" +
                    "Ваша дата: " + film.getReleaseDate());
        }
    }

    private void validateDuration(Film film) {
        if (film.getDuration() < 0) {
            throw new FilmValidateException("Продолжительность фильма не может быть отрицательной");
        }
    }
}

package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FilmService {
    private long serial = 1;

    public void update(List<Film> films, Film target) {
        Optional<Film> optionalFilm = films.stream()
                .filter(film -> Objects.equals(film.getId(), target.getId()))
                .findFirst();

        int index = films.indexOf(optionalFilm.orElseThrow(
                () -> new NotFoundByIdException(String.format("Фильм с id=%d не найден", target.getId()))
        ));
        films.set(index, target);
    }

    public Film validate(Film film) {
        validateName(film);
        validateDescription(film);
        validateReleaseDate(film);
        validateDuration(film);
        setId(film);
        return film;
    }

    private void validateName(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
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

    private void setId(Film film) {
        if (film.getId() == 0) {
            film.setId(uniqueId());
        }
    }

    private long uniqueId() {
        return serial++;
    }
}

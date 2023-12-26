package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.model.Genre;
import kz.runtime.dayardiyev.filmorate.storage.GenreStorage;
import kz.runtime.dayardiyev.filmorate.storage.impl.MpaDbStorage;
import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.model.Mpa;
import kz.runtime.dayardiyev.filmorate.storage.FilmStorage;
import kz.runtime.dayardiyev.filmorate.storage.LikeStorage;
import kz.runtime.dayardiyev.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final MpaDbStorage mpaStorage;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;

    public Film create(Film film) {
        return filmStorage.create(validate(film));
    }

    public Film update(Film film) {
        return filmStorage.update(validate(film));
    }

    public Film findById(int id) {
        Film film = filmStorage.findById(id).orElseThrow(() -> new NotFoundByIdException("Фильм с id = " + id + " не найден"));
        addGenres(film);
        return film;
    }


    public List<Film> findAll() {
        List<Film> films = filmStorage.findAll();
        films.forEach(this::addGenres);
        return films;
    }


    public List<Film> findAllPopular(int count) {
        List<Film> films = filmStorage.findAllPopular(count);
        films.forEach(this::addGenres);
        return films;
    }

    public void addLike(int id, int userId) {
        findById(id);
        userStorage.findById(userId);
        likeStorage.addLike(id, userId);
    }

    public void removeLike(int id, int userId) {
        findById(id);
        userStorage.findById(userId);
        likeStorage.removeLike(id, userId);
    }

    public List<Mpa> findAllMpa() {
        return mpaStorage.findAll();
    }

    public Mpa findMpaById(int id) {
        return mpaStorage.findById(id).orElseThrow(() -> new NotFoundByIdException("MPA рейтинг с id =" + id + " не найден"));
    }

    public Film validate(Film film) {
        validateName(film);
        validateDescription(film);
        validateReleaseDate(film);
        validateDuration(film);
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

    private void addGenres(Film film) {
        Set<Genre> genres = genreStorage.findAllGenresByFilm(film.getId());
        if (genres != null) {
            film.addAllGenres(genres);
        }
    }
}

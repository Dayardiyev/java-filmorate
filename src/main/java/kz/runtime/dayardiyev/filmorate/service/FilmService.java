package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.storage.InMemoryFilmStorage;
import kz.runtime.dayardiyev.filmorate.storage.InMemoryUserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;


@Slf4j
@Service
public class FilmService extends AbstractService<Film, InMemoryFilmStorage> {

    private final InMemoryUserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage storage, InMemoryUserStorage userStorage) {
        super(storage);
        this.userStorage = userStorage;
    }

    public void addLike(long id, long userId) {
        if (userStorage.contains(userId)) {
            storage.addLike(id, userId);
        } else {
            throw new NotFoundByIdException("");
        }
    }

    public void removeLike(long id, long userId) {
        if (storage.contains(id) && userStorage.contains(id)) {
            storage.removeLike(id, userId);
            return;
        }
        throw new NotFoundByIdException("Сущность не найден по id");
    }

    public Set<Film> getFilmsByCount(Integer count) {
        return storage.getFilmsByCount(count);
    }

    @Override
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

}

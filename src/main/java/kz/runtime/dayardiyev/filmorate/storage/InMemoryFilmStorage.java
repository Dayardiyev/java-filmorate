package kz.runtime.dayardiyev.filmorate.storage;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage extends AbstractInMemoryStorage<Film> implements FilmStorage {

    private final InMemoryUserStorage userStorage;

    @Autowired
    public InMemoryFilmStorage(InMemoryUserStorage userStorage) {
        super(Film.class);
        this.userStorage = userStorage;
    }

    private static final int DEFAULT_FILM_COUNT = 10;

    @Override
    public void addLike(long id, long userId) {
        if (contains(id) && userStorage.contains(userId)) {
            entities.get(id).addLike(userId);
            return;
        }
        throw new NotFoundByIdException("Объект с переданным id не найден");
    }

    @Override
    public void removeLike(long id, long userId) {
        if (contains(id) && userStorage.contains(userId)) {
            entities.get(id).removeLike(userId);
            return;
        }
        throw new NotFoundByIdException("Объект с переданным id не найден");
    }

    @Override
    public Set<Film> getFilmsByCount(Integer count) {
        // Проверка и установка значения по умолчанию для count
        if (count == null || count < 0) {
            count = DEFAULT_FILM_COUNT;
        }

        // Создание TreeSet с компаратором для сортировки по количеству лайков
        TreeSet<Film> sortedFilms = new TreeSet<>((o1, o2) -> Integer.compare(o2.getLikes().size(), o1.getLikes().size()));
        sortedFilms.addAll(entities.values());

        // Ограничение отсортированного набора до count элементов
        return sortedFilms.stream().limit(count).collect(Collectors.toCollection(LinkedHashSet::new));
    }

}

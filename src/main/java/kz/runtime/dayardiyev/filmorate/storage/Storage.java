package kz.runtime.dayardiyev.filmorate.storage;

import java.util.List;
import java.util.Optional;

public interface Storage<T> {
    Optional<T> findById(int id);

    List<T> findAll();
}

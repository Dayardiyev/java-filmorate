package kz.runtime.dayardiyev.filmorate.storage;

import java.util.List;

public interface Storage<T> {
    T save(T entity);
    List<T> findAll();
}

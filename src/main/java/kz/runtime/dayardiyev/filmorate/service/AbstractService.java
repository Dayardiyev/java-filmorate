package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.AbstractModel;
import kz.runtime.dayardiyev.filmorate.storage.AbstractInMemoryStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class AbstractService<T extends AbstractModel, S extends AbstractInMemoryStorage<T>> {

    protected final S storage;

    public T create(T entity) {
        T validated = validate(entity);
        storage.save(validated);
        return validated;
    }

    public T update(T entity) {
        long id = entity.getId();
        if (storage.contains(id)) {
            T validated = validate(entity);
            return storage.save(validated);
        }
        throw new NotFoundByIdException(entity.getClass().getSimpleName() + " с id " + id + " не найден");
    }

    public List<T> findAll(){
        return storage.findAll();
    }

    public T getById(long id) {
        if (storage.contains(id)) {
            return storage.getEntities().get(id);
        }
        throw new NotFoundByIdException("Сущность с id " + id + " не найден");
    }

    public abstract T validate(T t);
}

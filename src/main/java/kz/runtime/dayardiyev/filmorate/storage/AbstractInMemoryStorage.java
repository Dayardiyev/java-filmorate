package kz.runtime.dayardiyev.filmorate.storage;

import kz.runtime.dayardiyev.filmorate.model.AbstractModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractInMemoryStorage<T extends AbstractModel> implements Storage<T> {

    @Getter
    protected final Map<Long, T> entities;
    private final String className;
    private long serial;

    public AbstractInMemoryStorage(Class<T> entityClass) {
        this.entities = new HashMap<>();
        this.className = entityClass.getSimpleName();
        serial = 1;
    }

    public T save(T entity) {
        setId(entity);
        entities.put(entity.getId(), entity);

        log.info("{} успешно был добавлен", className);
        return entity;
    }

    public List<T> findAll() {
        log.info("Просмотрен список {}", className);
        return new ArrayList<>(entities.values());
    }

    public boolean contains(long id) {
        return entities.containsKey(id);
    }

    private void setId(T entity) {
        if (entity.getId() == 0) {
            entity.setId(uniqueId());
        }
    }

    private long uniqueId() {
        return serial++;
    }

}
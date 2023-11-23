package kz.runtime.dayardiyev.filmorate.storage;

import kz.runtime.dayardiyev.filmorate.model.AbstractModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public abstract class AbstractInMemoryStorage<T extends AbstractModel> implements Storage<T> {

    @Getter
    protected final Map<Long, T> entities;

    private long serial;

    public AbstractInMemoryStorage() {
        this.entities = new HashMap<>();
        serial = 1;
    }

    public T save(T entity) {
        setId(entity);
        entities.put(entity.getId(), entity);

        log.info("{} успешно был добавлен", entity.getClass().getSimpleName());
        return entity;
    }


    public List<T> findAll() {
        Collection<T> values = entities.values();

        if (values.isEmpty()) {
            log.debug("Просмотрен список. Список пуст");
        } else {
            T firstElement = values.iterator().next();
            log.debug("Просмотрен список {}", firstElement.getClass().getSimpleName());
        }

        return new ArrayList<>(values);
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
package kz.runtime.dayardiyev.filmorate.storage;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage extends AbstractInMemoryStorage<User> implements UserStorage {

    @Override
    public void addFriend(long id, long friendId) {
        if (contains(id) && contains(friendId)) {
            entities.get(id).addFriend(friendId);
            entities.get(friendId).addFriend(id);
            return;
        }
        throw new NotFoundByIdException("Пользователь не найден");
    }

    @Override
    public void deleteFriend(long id, long friendId) {
        if (contains(id) && contains(friendId)) {
            entities.get(id).deleteFriend(friendId);
            entities.get(friendId).deleteFriend(id);
            return;
        }
        throw new NotFoundByIdException("Пользователь не найден");
    }

    // Получение списка друзей пользователя
    // метод не может вернуть null, поэтому в случае отсутствия друзей возвращается пустой список
    @Override
    public List<User> getFriends(long id) {
        return Optional.ofNullable(entities.get(id))
                .map(User::getFriends)
                .orElse(Collections.emptySet())
                .stream()
                .map(entities::get)
                .collect(Collectors.toList());
    }


    // Получение списка общих друзей двух пользователей
    // проверяется наличие пользователей в хранилище
    // и возвращается список общих друзей
    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        return Optional.ofNullable(entities.get(id))
                .map(User::getFriends)
                .orElse(Collections.emptySet())
                .stream()
                .filter(new HashSet<>(Optional.ofNullable(entities.get(otherId))
                        .map(User::getFriends)
                        .orElse(Collections.emptySet()))::contains)
                .map(entities::get)
                .collect(Collectors.toList());
    }
}


package kz.runtime.dayardiyev.filmorate.storage;

import kz.runtime.dayardiyev.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage extends AbstractInMemoryStorage<User> implements UserStorage {

    public InMemoryUserStorage() {
        super("User");
    }

    @Override
    public void addFriend(long id, long friendId) {
        entities.get(id).addFriend(friendId);
        entities.get(friendId).addFriend(id);
    }

    @Override
    public void deleteFriend(long id, long friendId) {
        entities.get(id).deleteFriend(friendId);
        entities.get(friendId).deleteFriend(id);
    }

    @Override
    public List<User> getFriends(long id) {
        return Optional.ofNullable(entities.get(id))
                .map(User::getFriends)
                .orElse(Collections.emptySet())
                .stream()
                .map(entities::get)
                .collect(Collectors.toList());
    }


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


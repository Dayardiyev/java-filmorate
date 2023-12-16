package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.exception.UserValidateException;
import kz.runtime.dayardiyev.filmorate.model.User;
import kz.runtime.dayardiyev.filmorate.storage.FriendStorage;
import kz.runtime.dayardiyev.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage storage;
    private final FriendStorage friendStorage;

    public User findById(int id) {
        return storage.findById(id).orElseThrow(() -> new NotFoundByIdException("Пользователь с id=" + id + " не найден!"));
    }

    public List<User> findAll() {
        return storage.findAll();
    }

    public User create(User user) {
        return storage.create(validate(user));
    }

    public User update(User user) {
        return storage.update(validate(user));
    }

    public void addFriend(int id, int otherId) {
        findById(id);
        findById(otherId);
        friendStorage.addFriend(id, otherId);
    }

    public void removeFriend(int id, int otherId) {
        findById(id);
        findById(otherId);
        friendStorage.removeFriend(id, otherId);

    }

    public List<User> findAllFriends(int id) {
        return friendStorage.findAllFriends(id);
    }

    public List<User> findAllCommonFriends(int id, int otherId) {
        return friendStorage.findAllCommonFriends(id, otherId);
    }

    public User validate(User user) {
        validateEmail(user);
        validateLogin(user);
        validateName(user);
        validateBirthday(user);
        return user;
    }

    private void validateEmail(User user) {
        String email = user.getEmail();
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new UserValidateException("Электронная почта не может быть пустой и должна содержать символ @");
        }
    }

    private void validateLogin(User user) {
        String login = user.getLogin();
        if (login.isBlank() || login.contains(" ")) {
            throw new UserValidateException("Логин не может быть пустым и содержать пробелы");
        }
    }

    private void validateName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void validateBirthday(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new UserValidateException("Дата рождения не может быть в будущем");
        }
    }
}

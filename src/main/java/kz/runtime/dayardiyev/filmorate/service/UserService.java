package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.exception.UserValidateException;
import kz.runtime.dayardiyev.filmorate.model.User;
import kz.runtime.dayardiyev.filmorate.storage.InMemoryUserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService extends AbstractService<User, InMemoryUserStorage> {

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        super(storage);
    }

    @Override
    public User validate(User user) {
        validateEmail(user);
        validateLogin(user);
        validateName(user);
        validateBirthday(user);
        return user;
    }

    public void addFriend(long id, long friendId){
        if (storage.contains(id) && storage.contains(friendId)){
            storage.addFriend(id, friendId);
            return;
        }
        throw new NotFoundByIdException("Пользователь не найден");
    }

    public void deleteFriend(long id, long friendId) {
        if (storage.contains(id) && storage.contains(friendId)){
            storage.deleteFriend(id, friendId);
            return;
        }
        throw new NotFoundByIdException("Пользователь не найден");
    }

    public List<User> getFriends(long id) {
        return storage.getFriends(id);
    }

    public List<User> getCommonFriends(long id, long friendId) {
        return storage.getCommonFriends(id, friendId);
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

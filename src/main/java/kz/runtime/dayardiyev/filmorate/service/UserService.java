package kz.runtime.dayardiyev.filmorate.service;

import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.exception.UserValidateException;
import kz.runtime.dayardiyev.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserService {
    private long serial = 1;

    public void update(List<User> users, User target) {
        Optional<User> optionalUser = users.stream()
                .filter(user -> Objects.equals(user.getId(), target.getId()))
                .findFirst();

        int index = users.indexOf(optionalUser.orElseThrow(
                () -> new NotFoundByIdException(String.format("Пользователь с id=%d не найден", target.getId()))
        ));
        users.set(index, target);
    }

    public User validate(User user) {
        validateEmail(user);
        validateLogin(user);
        validateName(user);
        validateBirthday(user);
        setId(user);
        return user;
    }

    private void validateEmail(User user) {
        String email = user.getEmail();
        if (email == null) {
            throw new UserValidateException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (email.isBlank() || !email.contains("@")) {
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

    private void setId(User user){
        if (user.getId() == 0){
            user.setId(uniqueId());
        }
    }

    private long uniqueId(){
        return serial++;
    }
}

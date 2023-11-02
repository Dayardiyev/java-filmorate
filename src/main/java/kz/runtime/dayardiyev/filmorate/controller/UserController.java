package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.model.User;
import kz.runtime.dayardiyev.filmorate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final List<User> users = new ArrayList<>();
    private final UserService userService = new UserService();

    @PostMapping
    public User createUser(@RequestBody User user) {
        User validatedUser = userService.validate(user);
        users.add(validatedUser);
        log.info("Пользователь успешно был создан");
        return validatedUser;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        User validatedUser = userService.validate(user);
        userService.update(users, user);
        log.info("Пользователь успешно был обновлен!");
        return validatedUser;
    }

    @GetMapping
    public List<User> findAll() {
        log.info("Просмотрен список пользователей");
        return users;
    }
}

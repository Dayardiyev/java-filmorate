package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.exception.UserValidateException;
import kz.runtime.dayardiyev.filmorate.model.User;
import kz.runtime.dayardiyev.filmorate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            User validatedUser = userService.validate(user);
            users.add(validatedUser);
            log.info("Пользователь успешно был создан");
            return ResponseEntity.ok(validatedUser);
        } catch (UserValidateException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        try {
            User validatedUser = userService.validate(user);
            userService.update(users, user);
            log.info("Пользователь успешно был обновлен!");
            return ResponseEntity.ok(validatedUser);
        } catch (UserValidateException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }
    }

    @GetMapping
    public List<User> findAll() {
        log.info("Просмотрен список пользователей");
        return users;
    }
}

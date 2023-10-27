package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping
    public User createUser(@RequestBody User user) {
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return user;
    }

    @GetMapping
    public List<User> findAll() {
        return users;
    }
}

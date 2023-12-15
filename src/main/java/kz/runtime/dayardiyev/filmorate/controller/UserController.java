package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.model.User;
import kz.runtime.dayardiyev.filmorate.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @PostMapping
    public User create(@RequestBody User user) {
        log.info("POST / user / {}", user.getLogin());
        service.create(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        log.info("PUT / user / {}", user.getLogin());
        service.update(user);
        return user;
    }

    @GetMapping
    public List<User> findAll() {
        log.info("GET / users");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") int id) {
        log.info("GET / users / {}", id);
        return service.findById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId) {
        log.info("PUT / {} / friends / {}", id, friendId);
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId) {
        log.info("PUT / {} / friends / {}", id, friendId);
        service.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findAllFriends(@PathVariable("id") int id) {
        log.info("GET / {} / friends", id);
        return service.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable("id") int id, @PathVariable("otherId") int otherId) {
        log.info("GET / {} / friends / common / {}", id, otherId);
        return service.getAllCommonFriends(id, otherId);
    }
}


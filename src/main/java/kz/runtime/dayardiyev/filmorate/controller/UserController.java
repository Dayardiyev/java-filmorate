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
    public User createUser(@RequestBody User user) {
        return service.create(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return service.update(user);
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
        return service.getById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId){
        service.addFriend(id,friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId){
        service.deleteFriend(id,friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable long id){
        return service.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId){
        return service.getCommonFriends(id, otherId);
    }
}


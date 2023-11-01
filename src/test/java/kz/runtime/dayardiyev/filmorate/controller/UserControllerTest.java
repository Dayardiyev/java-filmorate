package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UserController.class)
public class UserControllerTest {

    static UserController userController;
    User userToTest;

    @BeforeAll
    public static void initController() {
        userController = new UserController();
    }

    @BeforeEach
    public void init() {
        userToTest = new User(
                1,
                "example@mail.com",
                "user_login",
                "user_name",
                LocalDate.of(2000, 1, 1)
        );
    }

    @Test
    public void createUserTest() {
        ResponseEntity<Object> result = userController.createUser(userToTest);
        assertEquals(result.getBody(), userToTest);
    }

    @Test
    public void createUserTestWrongEmail() {
        userToTest.setEmail("");
        ResponseEntity<Object> result = userController.createUser(userToTest);
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUserTestWrongLogin() {
        userToTest.setLogin("admin 1234");
        ResponseEntity<Object> result = userController.createUser(userToTest);
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUserTestEmptyName() {
        userToTest.setName(null);
        ResponseEntity<Object> result = userController.createUser(userToTest);
        assertEquals(result.getBody(), userToTest);
    }

    @Test
    public void createUserTestWrongBirthday() {
        userToTest.setBirthday(LocalDate.now().plusDays(100));
        ResponseEntity<Object> result = userController.createUser(userToTest);
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}

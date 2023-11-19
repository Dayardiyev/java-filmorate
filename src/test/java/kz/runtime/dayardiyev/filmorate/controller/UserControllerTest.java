package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.exception.UserValidateException;
import kz.runtime.dayardiyev.filmorate.model.User;
import kz.runtime.dayardiyev.filmorate.service.UserService;
import kz.runtime.dayardiyev.filmorate.storage.InMemoryUserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = UserController.class)
public class UserControllerTest {

    UserController userController;
    User userToTest;


    @BeforeEach
    public void init() {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
        userToTest = new User(1, "example@mail.com", "user_login", "user_name", LocalDate.of(2000, 1, 1));
    }

    @Test
    public void createUserTest() {
        User result = userController.createUser(userToTest);

        assertEquals(result, userToTest);
        assertEquals(1, userController.findAll().size());
    }

    @Test
    public void createUserTestWrongEmail() {
        userToTest.setEmail("");

        UserValidateException e = assertThrows(UserValidateException.class, () -> userController.createUser(userToTest));

        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", e.getMessage());
        assertEquals(0, userController.findAll().size());
    }

    @Test
    public void createUserTestWrongLogin() {
        userToTest.setLogin("admin 1234");

        UserValidateException e = assertThrows(UserValidateException.class, () -> userController.createUser(userToTest));

        assertEquals("Логин не может быть пустым и содержать пробелы", e.getMessage());
        assertEquals(0, userController.findAll().size());
    }

    @Test
    public void createUserTestEmptyName() {
        userToTest.setName(null);

        User result = userController.createUser(userToTest);

        assertEquals(1, userController.findAll().size());
        assertEquals(userToTest.getLogin(), result.getLogin());
    }

    @Test
    public void createUserTestWrongBirthday() {
        userToTest.setBirthday(LocalDate.now().plusDays(100));

        UserValidateException e = assertThrows(UserValidateException.class, () -> userController.createUser(userToTest));

        assertEquals("Дата рождения не может быть в будущем", e.getMessage());
        assertEquals(0, userController.findAll().size());
    }
}

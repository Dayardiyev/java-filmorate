package kz.runtime.dayardiyev.filmorate.controller;

import kz.runtime.dayardiyev.filmorate.exception.UserValidateException;
import kz.runtime.dayardiyev.filmorate.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;


    User userToTest;


    @BeforeEach
    public void init() {
        userToTest = User.builder()
                .email("example@gmail.com")
                .login("user_login")
                .name("user_name")
                .birthday(LocalDate.of(2000, 2, 2))
                .build();
    }

    @Test
    public void createUserTest() {
        User result = userController.create(userToTest);

        assertEquals(result, userToTest);
    }

    @Test
    public void createUserTestWrongEmail() {
        userToTest.setEmail("");

        Executable executable = () -> userController.create(userToTest);

        UserValidateException e = assertThrows(UserValidateException.class, executable);
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", e.getMessage());
    }

    @Test
    public void createUserTestWrongLogin() {
        userToTest.setLogin("admin 1234");

        Executable executable = () -> userController.create(userToTest);

        UserValidateException e = assertThrows(UserValidateException.class, executable);
        assertEquals("Логин не может быть пустым и содержать пробелы", e.getMessage());
    }


    @Test
    public void createUserTestWrongBirthday() {
        userToTest.setBirthday(LocalDate.now().plusDays(100));

        Executable executable = () -> userController.create(userToTest);

        UserValidateException e = assertThrows(UserValidateException.class, executable);
        assertEquals("Дата рождения не может быть в будущем", e.getMessage());
    }
}

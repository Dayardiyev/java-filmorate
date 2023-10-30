package main.test.java.kz.runtime.dayardiyev.filmorate.controller;


import kz.runtime.dayardiyev.filmorate.controller.FilmController;
import kz.runtime.dayardiyev.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@SpringBootTest(classes = FilmController.class)
public class FilmControllerTest {
    static FilmController controller;
    Film filmToTest;

    @BeforeAll
    public static void initController() {
        controller = new FilmController();

    }

    @BeforeEach
    public void init() {
        filmToTest = new Film(
                1,
                "film_name",
                "",
                LocalDate.of(2000, 1, 1),
                120
        );
    }


    @Test
    public void createUserTest() {
        ResponseEntity<Object> result = controller.createFilm(filmToTest);
        assertEquals(result.getBody(), filmToTest);
    }


    @Test
    public void createUserTestWrongLogin() {
        filmToTest.setName(" ");
        ResponseEntity<Object> result = controller.createFilm(filmToTest);
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUserTestWrongDescription() {
        filmToTest.setDescription("*".repeat(203));
        ResponseEntity<Object> result = controller.createFilm(filmToTest);
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUserTestWrongReleaseDate() {
        filmToTest.setReleaseDate(LocalDate.of(1895, 12, 27));
        ResponseEntity<Object> result = controller.createFilm(filmToTest);
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUserTestWrongDuration() {
        filmToTest.setDuration(-1);
        ResponseEntity<Object> result = controller.createFilm(filmToTest);
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}


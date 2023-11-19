package kz.runtime.dayardiyev.filmorate.controller;


import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FilmControllerTest {

    @Autowired
    private FilmController controller;

    Film filmToTest;


    @BeforeEach
    public void init() {
        filmToTest = new Film(1, "film_name", "", LocalDate.of(2000, 1, 1), 120);
    }

    @Test
    public void createFilmTest() {
        Film result = controller.createFilm(filmToTest);

        assertEquals(filmToTest, result);
    }


    @Test
    public void createFilmTestWrongName() {
        filmToTest.setName(" ");

        FilmValidateException e = assertThrows(FilmValidateException.class, () -> controller.createFilm(filmToTest));

        assertEquals("Название фильма не должно быть пустым!", e.getMessage());
    }

    @Test
    public void createFilmTestWrongDescription() {
        filmToTest.setDescription("*".repeat(203));

        FilmValidateException e = assertThrows(FilmValidateException.class, () -> controller.createFilm(filmToTest));

        assertEquals("Максимальное количество символов для описания фильма: 200\n" + "Количество символов в вашем описании: " + filmToTest.getDescription().length(), e.getMessage());
    }

    @Test
    public void createFilmTestWrongReleaseDate() {
        filmToTest.setReleaseDate(LocalDate.of(1895, 12, 27));

        FilmValidateException e = assertThrows(FilmValidateException.class, () -> controller.createFilm(filmToTest));

        assertEquals("Дата релиза фильма не должна быть раньше 28 декабря 1895 года\n" + "Ваша дата: " + filmToTest.getReleaseDate(), e.getMessage());
    }

    @Test
    public void createFilmTestWrongDuration() {
        filmToTest.setDuration(-1);

        FilmValidateException e = assertThrows(FilmValidateException.class, () -> controller.createFilm(filmToTest));

        assertEquals("Продолжительность фильма не может быть отрицательной", e.getMessage());
    }
}


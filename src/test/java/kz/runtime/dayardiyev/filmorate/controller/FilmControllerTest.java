package kz.runtime.dayardiyev.filmorate.controller;


import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.model.Mpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
        filmToTest = Film.builder()
                .name("film_name")
                .description("")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .mpa(new Mpa(1, ""))
                .build();
    }

    @Test
    public void createFilmTest() {
        Film result = controller.create(filmToTest);

        assertEquals(filmToTest, result);
    }


    @Test
    public void createFilmTestWrongName() {
        filmToTest.setName(" ");

        Executable executable = () -> controller.create(filmToTest);

        FilmValidateException e = assertThrows(FilmValidateException.class, executable);
        assertEquals("Название фильма не должно быть пустым!", e.getMessage());
    }

    @Test
    public void createFilmTestWrongDescription() {
        filmToTest.setDescription("*".repeat(203));

        Executable executable = () -> controller.create(filmToTest);

        FilmValidateException e = assertThrows(FilmValidateException.class, executable);
        assertEquals("Максимальное количество символов для описания фильма: 200\n" + "Количество символов в вашем описании: " + filmToTest.getDescription().length(), e.getMessage());
    }

    @Test
    public void createFilmTestWrongReleaseDate() {
        filmToTest.setReleaseDate(LocalDate.of(1895, 12, 27));

        Executable executable = () -> controller.create(filmToTest);

        FilmValidateException e = assertThrows(FilmValidateException.class, executable);
        assertEquals("Дата релиза фильма не должна быть раньше 28 декабря 1895 года\n" + "Ваша дата: " + filmToTest.getReleaseDate(), e.getMessage());
    }

    @Test
    public void createFilmTestWrongDuration() {
        filmToTest.setDuration(-1);

        Executable executable = () -> controller.create(filmToTest);

        FilmValidateException e = assertThrows(FilmValidateException.class, executable);
        assertEquals("Продолжительность фильма не может быть отрицательной", e.getMessage());
    }
}


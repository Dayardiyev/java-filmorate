package kz.runtime.dayardiyev.filmorate;

import kz.runtime.dayardiyev.filmorate.exception.FilmValidateException;
import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.service.FilmService;

import java.time.Duration;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        FilmService filmService = new FilmService();
//        Film f1 = new Film(1L, "s", "", LocalDate.of(2022, 2, 2), Duration.ofMinutes(2));
//        Film f2 = new Film(1L, "s", "", LocalDate.of(2022, 2, 2), Duration.ofMinutes(2));
//        System.out.println(f1.equals(f2));
//        try {
//            filmService.validate(new Film(1, "s", "", LocalDate.of(2022, 2, 2), Duration.ofMinutes(2)));
//            System.out.println("Проверка пройдена");
//        } catch (FilmValidateException e) {
//            System.out.println(e.getMessage());
//        }
    }
}

package kz.runtime.dayardiyev.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    public User(Integer id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }
}

package kz.runtime.dayardiyev.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Data
@Builder
public class Film {

    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Mpa mpa;
    private final LinkedHashSet<Genre> genres = new LinkedHashSet<>();

    public Film setId(int id) {
        this.id = id;
        return this;
    }

    public void addAllGenres(Set<Genre> genres) {
        this.genres.addAll(genres);
    }
}

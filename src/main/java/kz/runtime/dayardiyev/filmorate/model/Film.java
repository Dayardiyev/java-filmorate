package kz.runtime.dayardiyev.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    @NonNull
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}

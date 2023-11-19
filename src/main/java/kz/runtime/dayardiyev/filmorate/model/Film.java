package kz.runtime.dayardiyev.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Film extends AbstractModel {
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Long> likes = new HashSet<>();

    public void addLike(long id){
        likes.add(id);
    }

    public void removeLike(long id){
        likes.add(id);
    }
}

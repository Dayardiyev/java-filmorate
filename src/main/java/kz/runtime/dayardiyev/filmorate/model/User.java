package kz.runtime.dayardiyev.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends AbstractModel {
    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friends = new TreeSet<>(Long::compare);

    public void addFriend(long id) {
        friends.add(id);
    }

    public void deleteFriend(long id) {
        friends.remove(id);
    }
}

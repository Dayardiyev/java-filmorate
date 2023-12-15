package kz.runtime.dayardiyev.filmorate.storage;

import kz.runtime.dayardiyev.filmorate.model.User;


public interface UserStorage extends Storage<User> {
    User create(User user);

    User update(User user);
}

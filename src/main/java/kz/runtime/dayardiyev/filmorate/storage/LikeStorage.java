package kz.runtime.dayardiyev.filmorate.storage;

public interface LikeStorage {
    void addLike(int id, int userId);

    void removeLike(int id, int userId);
}

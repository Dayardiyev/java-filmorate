package kz.runtime.dayardiyev.filmorate.dao;

import kz.runtime.dayardiyev.filmorate.storage.LikeStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(int id, int userId) {
        String sql = "insert into likes (film_id, user_id) values (?, ?)";
        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public void removeLike(int id, int userId) {
        String sql = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }

}

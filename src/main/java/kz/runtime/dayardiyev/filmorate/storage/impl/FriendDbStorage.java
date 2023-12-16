package kz.runtime.dayardiyev.filmorate.storage.impl;

import kz.runtime.dayardiyev.filmorate.model.User;
import kz.runtime.dayardiyev.filmorate.storage.FriendStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(int userId, int friendId) {
        String sql = "insert into user_friends (user_id, friend_id) values (?,?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        String sql = "delete from user_friends where user_id in (?, ?) and friend_id in (?, ?)";
        jdbcTemplate.update(sql, userId, friendId, userId, friendId);
    }

    @Override
    public List<User> findAllFriends(int id) {
        String sql = "select u.id, u.email, u.login, u.name, u.birthday " +
                "from user_friends as f " +
                "join users as u on u.id = f.friend_id " +
                "where f.user_id = ? " +
                "order by u.id";
        return jdbcTemplate.query(sql, rowMapper(), id);
    }

    @Override
    public List<User> findAllCommonFriends(int id, int otherId) {
        String sql = "select u.id, u.email, u.login, u.name, u.birthday " +
                "from user_friends as f " +
                "join user_friends fr on fr.friend_id = f.friend_id " +
                "join users u on u.id = fr.friend_id " +
                "where f.user_id = ? and fr.user_id = ? " +
                "and f.friend_id <> fr.user_id and fr.friend_id <> f.user_id";
        return jdbcTemplate.query(sql, rowMapper(), id, otherId);
    }


    private RowMapper<User> rowMapper() {
        return (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }
}

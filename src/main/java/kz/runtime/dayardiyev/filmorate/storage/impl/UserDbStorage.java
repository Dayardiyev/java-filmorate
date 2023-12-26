package kz.runtime.dayardiyev.filmorate.storage.impl;


import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.User;
import kz.runtime.dayardiyev.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findById(int id) {
        String sql = "select * from users where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper(), id));
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        int id = jdbcInsert.executeAndReturnKey(toMap(user)).intValue();
        return user.setId(id);
    }

    @Override
    public User update(User user) {
        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?";
        int result = jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), Date.valueOf(user.getBirthday()), user.getId());
        if (result == 0) {
            throw new NotFoundByIdException("Пользователь с id=" + user.getId() + " не найден");
        }
        return user;
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

    private Map<String, Object> toMap(User user) {
        return Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", Date.valueOf(user.getBirthday())
        );
    }
}

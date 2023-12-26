package kz.runtime.dayardiyev.filmorate.storage.impl;

import kz.runtime.dayardiyev.filmorate.model.Mpa;
import kz.runtime.dayardiyev.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Mpa> findById(int id) {
        String sql = "select * from rating_mpa where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper(), id));
    }

    @Override
    public List<Mpa> findAll() {
        String sql = "select * from rating_mpa order by id";
        return jdbcTemplate.query(sql, rowMapper());
    }

    private RowMapper<Mpa> rowMapper() {
        return ((rs, rowNum) -> Mpa.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build());
    }
}

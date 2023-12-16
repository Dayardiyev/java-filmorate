package kz.runtime.dayardiyev.filmorate.storage.impl;

import kz.runtime.dayardiyev.filmorate.model.Genre;
import kz.runtime.dayardiyev.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Genre> findById(int id) {
        String query = "select * from genres where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, rowMapper(), id));
    }

    @Override
    public List<Genre> findAll() {
        String query = "select * from genres order by id";
        return jdbcTemplate.query(query, rowMapper());
    }

    @Override
    public Set<Genre> findAllGenresByFilm(int id) {
        String sql = "select g.id, g.name from films_genres as fg " +
                "join genres as g on fg.genre_id = g.id WHERE fg.film_id = ?";
        return new LinkedHashSet<>(jdbcTemplate.query(sql, rowMapper(), id));
    }

    private RowMapper<Genre> rowMapper() {
        return ((rs, rowNum) -> Genre.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build());
    }
}

package kz.runtime.dayardiyev.filmorate.dao;

import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.model.Genre;
import kz.runtime.dayardiyev.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.UnaryOperator.identity;

@RequiredArgsConstructor
@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Genre> findById(int id) {
        final String query = "select * from genres where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, genreRowMapper(), id));
    }

    @Override
    public List<Genre> findAll() {
        final String query = "select * from genres order by id";
        return jdbcTemplate.query(query, genreRowMapper());
    }

    public void findAllGenresByFilm(List<Film> films) {
        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, identity()));
        String sql = "SELECT * FROM GENRES g, films_genres fg WHERE fg.genre_id = g.id AND fg.film_id in (%s)";
        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        jdbcTemplate.query(String.format(sql, inSql),
                filmById.keySet().toArray(),
                (rs, rowNum) -> filmById.get(rs.getInt("film_id")).getGenres().add(makeGenre(rs)));
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("genre_id");
        String name = rs.getString("name");
        return Genre.builder()
                .id(id)
                .name(name)
                .build();
    }

    private RowMapper<Genre> genreRowMapper() {
        return ((rs, rowNum) -> Genre.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build());
    }
}

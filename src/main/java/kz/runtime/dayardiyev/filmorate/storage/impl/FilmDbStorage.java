package kz.runtime.dayardiyev.filmorate.storage.impl;


import kz.runtime.dayardiyev.filmorate.exception.NotFoundByIdException;
import kz.runtime.dayardiyev.filmorate.model.Film;
import kz.runtime.dayardiyev.filmorate.model.Mpa;
import kz.runtime.dayardiyev.filmorate.model.Genre;
import kz.runtime.dayardiyev.filmorate.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_FILMS =
            "select f.id, f.name, f.description, f.release_date, f.duration, " +
                    "mpa.id as mpa_id, mpa.name as mpa_name " +
                    "from films as f " +
                    "join rating_mpa as mpa on f.rating_mpa_id = mpa.id ";


    @Override
    public Film create(Film film) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        int id = insert.executeAndReturnKey(toMap(film)).intValue();
        System.out.println(film.getGenres());
        updateGenres(film.getGenres(), id);
        return film.setId(id);
    }

    @Override
    public Film update(Film film) {
        int id = film.getId();
        String sql = "update films set name = ?, description = ?, release_date = ?, duration = ?, rating_mpa_id = ? " +
                "where id = ?";
        int result = jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), id);

        if (result == 0) {
            throw new NotFoundByIdException("Фильм с id =" + id + " не найден");
        }
        updateGenres(film.getGenres(), id);
        return film;
    }

    @Override
    public List<Film> findAllPopular(int count) {
        String sql = "left join likes on f.id = likes.film_id " +
                "group by f.id " +
                "order by count(likes.film_id) desc " +
                "limit ?";
        return jdbcTemplate.query(SELECT_FILMS + sql, rowMapper(), count);
    }

    @Override
    public Optional<Film> findById(int id) {
        String sql = "where f.id = ?";
        Film film = jdbcTemplate.queryForObject(SELECT_FILMS + sql, rowMapper(), id);
        return Optional.ofNullable(film);
    }


    @Override
    public List<Film> findAll() {
        return jdbcTemplate.query(SELECT_FILMS, rowMapper());
    }


    private RowMapper<Film> rowMapper() {
        return ((rs, rowNum) -> Film.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .duration(rs.getInt("duration"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .mpa(new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")))
                .build());
    }

    private static Map<String, Object> toMap(Film film) {
        return Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "rating_mpa_id", film.getMpa().getId()
        );
    }

    private void updateGenres(Set<Genre> genres, int id) {
        jdbcTemplate.update("delete from films_genres where film_id = ?", id);
        if (genres != null && !genres.isEmpty()) {
            String sql = "insert into films_genres (film_id, genre_id) values (?, ?)";
            jdbcTemplate.batchUpdate(sql, genres, genres.size(),
                    (ps, genre) -> {
                        ps.setInt(1, id);
                        ps.setInt(2, genre.getId());
                    });
        }
    }
}

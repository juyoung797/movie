package com.j797.movie.repository;

import com.j797.movie.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Movie> movieRowMapper = (rs, rowNum) -> {
        Movie movie = Movie.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .releaseYear(rs.getInt("release_year"))
                .build();
        return movie;
    };

    public int save(Movie movie) {
        String sql = "INSERT INTO movies (title, release_year) VALUES (?, ?)";
        return jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseYear());
    }

    public List<Movie> findAll() {
        String sql = "SELECT * FROM movies";
        return jdbcTemplate.query(sql, movieRowMapper);
    }

    public Optional<Movie> findById(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, movieRowMapper, id));
    }

    public int update(Movie movie) {
        String sql = "UPDATE movies SET title = ? WHERE id = ?";
        return jdbcTemplate.update(sql, movie.getTitle(), movie.getId());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

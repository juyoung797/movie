package com.j797.movie.repository;

import com.j797.movie.model.MovieRate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRateRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MovieRate> movieRateRowMapper = (rs, rowNum) -> MovieRate.builder()
            .movieId(rs.getInt("movie_id"))
            .movieTitle(rs.getString("movie_title"))
            .avgRating(rs.getDouble("avg_rating"))
            .reviewCount(rs.getInt("review_count"))
            .build();

    public List<MovieRate> findAll() {
        String sql = "SELECT * FROM movie_average_rating";
        return jdbcTemplate.query(sql, movieRateRowMapper);
    }

    public MovieRate findByMovieId(int movieId) {
        String sql = "SELECT * FROM movie_average_rating WHERE movie_id = ?";
        return jdbcTemplate.queryForObject(sql, movieRateRowMapper, movieId);
    }
}

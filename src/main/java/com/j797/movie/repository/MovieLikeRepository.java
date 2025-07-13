package com.j797.movie.repository;

import com.j797.movie.model.MovieLike;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieLikeRepository {
    private final JdbcTemplate jdbcTemplate;

    public int likeMovie(int userId, int movieId) {
        String sql = "INSERT INTO liked_movies (user_id, movie_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, userId, movieId);
    }

    public int unlikeMovie(int userId, int movieId) {
        String sql = "DELETE FROM liked_movies WHERE user_id = ? AND movie_id = ?";
        return jdbcTemplate.update(sql, userId, movieId);
    }

    public boolean isMovieLiked(int userId, int movieId) {
        String sql = "SELECT COUNT(*) FROM liked_movies WHERE user_id = ? AND movie_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, movieId);
        return count != null && count > 0;
    }

    public List<Integer> findLikedMovieIdsByUser(int userId) {
        String sql = "SELECT movie_id FROM liked_movies WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("movie_id"), userId);
    }

    public List<Integer> findUserIdsByLikedMovie(int movieId) {
        String sql = "SELECT user_id FROM liked_movies WHERE movie_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), movieId);
    }
}

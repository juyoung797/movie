package com.j797.movie.repository;

import com.j797.movie.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Review> reviewRowMapper = (rs, rowNum) -> {
        Review review = Review.builder()
                .id(rs.getInt("id"))
                .movieId(rs.getInt("movie_id"))
                .userId(rs.getInt("user_id"))
                .rating(rs.getInt("rating"))
                .comment(rs.getString("comment"))
                .reviewedAt(rs.getTimestamp("reviewed_at").toLocalDateTime())
                .build();
        return review;
    };

    public int save(Review review) {
        String sql = "INSERT INTO reviews (movie_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, review.getMovieId(), review.getUserId(), review.getRating(), review.getComment());
    }

    public List<Review> findAll() {
        String sql = "SELECT * FROM reviews";
        return jdbcTemplate.query(sql, reviewRowMapper);
    }

    public List<Review> findByMovieId(int movieId) {
        String sql = "SELECT * FROM reviews WHERE movie_id = ?";
        return jdbcTemplate.query(sql, reviewRowMapper, movieId);
    }

    public List<Review> findByUserId(int userId) {
        String sql = "SELECT * FROM reviews WHERE user_id = ?";
        return jdbcTemplate.query(sql, reviewRowMapper, userId);
    }

    public Optional<Review> findById(int id) {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reviewRowMapper, id));
    }

    public int update(Review review) {
        String sql = "UPDATE reviews SET rating = ?, comment = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                review.getRating(),
                review.getComment(),
                review.getId());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM reviews WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

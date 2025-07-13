package com.j797.movie.repository;

import com.j797.movie.model.ShowTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShowTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ShowTime> showTimeRowMapper = (rs, rowNum) -> ShowTime.builder()
            .id(rs.getInt("id"))
            .time(rs.getTimestamp("time").toLocalDateTime())
            .location(rs.getString("location"))
            .movieId(rs.getInt("movie_id"))
            .build();

    // 저장
    public int save(ShowTime showTime) {
        String sql = "INSERT INTO showtime (time, location, movie_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                Timestamp.valueOf(showTime.getTime()),
                showTime.getLocation(),
                showTime.getMovieId());
    }

    // 전체 조회
    public List<ShowTime> findAll() {
        String sql = "SELECT * FROM showtime";
        return jdbcTemplate.query(sql, showTimeRowMapper);
    }

    // ID로 조회
    public ShowTime findById(int id) {
        String sql = "SELECT * FROM showtime WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, showTimeRowMapper, id);
    }

    // 수정 (시간, 위치, 영화 아이디 모두 수정 가능)
    public int update(ShowTime showTime) {
        String sql = "UPDATE showtime SET time = ?, location = ?, movie_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                Timestamp.valueOf(showTime.getTime()),
                showTime.getLocation(),
                showTime.getMovieId(),
                showTime.getId());
    }

    // 삭제
    public int deleteById(int id) {
        String sql = "DELETE FROM showtime WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

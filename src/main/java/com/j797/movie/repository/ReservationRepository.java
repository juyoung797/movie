package com.j797.movie.repository;

import com.j797.movie.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> Reservation.builder()
            .id(rs.getInt("id"))
            .userId(rs.getInt("user_id"))
            .eventId(rs.getInt("event_id"))
            .seats(rs.getInt("seats"))
            .reservedAt(rs.getTimestamp("reserved_at").toLocalDateTime())
            .build();

    // 예약 생성
    public int save(Reservation reservation) {
        String sql = "INSERT INTO reservations (event_id, user_id, seats) VALUES (:eventId, :userId, :seats)";
        Map<String, Object> params = Map.of(
                "eventId", reservation.getEventId(),
                "userId", reservation.getUserId(),
                "seats", reservation.getSeats()
        );
        return namedParameterJdbcTemplate.update(sql, params);
    }

    // event_id로 예약 목록 조회
    public List<Reservation> findByUserId(int userId) {
        String sql = "SELECT * FROM reservations WHERE user_id = :userId";
        Map<String, Object> params = Map.of("userId", userId);
        return namedParameterJdbcTemplate.query(sql, params, reservationRowMapper);
    }

    // id로 단일 예약 조회
    public Reservation findById(int id) {
        String sql = "SELECT * FROM reservations WHERE id = :id";
        Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, reservationRowMapper);
    }

    // 예약 수정 (seats 변경)
    public int updateSeats(int id, int seats) {
        String sql = "UPDATE reservations SET seats = :seats WHERE id = :id";
        Map<String, Object> params = Map.of("id", id, "seats", seats);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    // 예약 삭제
    public int deleteById(int id) {
        String sql = "DELETE FROM reservations WHERE id = :id";
        Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcTemplate.update(sql, params);
    }
}


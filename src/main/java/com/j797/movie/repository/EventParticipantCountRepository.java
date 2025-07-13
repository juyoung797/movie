package com.j797.movie.repository;

import com.j797.movie.model.EventParticipantCount;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class EventParticipantCountRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<EventParticipantCount> rowMapper = (rs, rowNum) -> {
        EventParticipantCount eventParticipantCount = EventParticipantCount.builder()
                .eventId(rs.getInt("event_id"))
                .eventTime(rs.getTimestamp("event_time").toLocalDateTime())
                .totalReservations(rs.getInt("total_reservations"))
                .totalSeats(rs.getInt("total_seats"))
                .build();
        return eventParticipantCount;
    };

    // 전체 조회
    public List<EventParticipantCount> findAll() {
        String sql = "SELECT * FROM event_participant_count ORDER BY event_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // 특정 이벤트 ID 조회
    public EventParticipantCount findByEventId(int eventId) {
        String sql = "SELECT * FROM event_participant_count WHERE event_id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, eventId);
    }
}

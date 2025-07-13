package com.j797.movie.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventParticipantCount {
    private Integer eventId;
    private LocalDateTime eventTime;
    private Integer totalReservations;
    private Integer totalSeats;
}

package com.j797.movie.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Reservation {
    private Integer id;
    private Integer userId;
    private Integer eventId;
    private Integer seats;
    private LocalDateTime reservedAt;
}

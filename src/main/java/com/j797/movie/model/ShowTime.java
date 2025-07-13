package com.j797.movie.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShowTime {
    private Integer id;
    private Integer movieId;
    private LocalDateTime time;
    private String location;
}

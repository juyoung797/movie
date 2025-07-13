package com.j797.movie.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Review {
    private Integer id;
    private Integer movieId;
    private Integer userId;
    private Integer rating;
    private String comment;
    private LocalDateTime reviewedAt;
}

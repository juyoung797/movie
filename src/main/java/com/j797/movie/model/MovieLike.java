package com.j797.movie.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MovieLike {
    private Integer userId;
    private Integer movieId;
    private LocalDateTime likedAt;
}

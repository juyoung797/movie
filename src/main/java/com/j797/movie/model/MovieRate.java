package com.j797.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieRate {
    private Integer movieId;
    private String movieTitle;
    private Double avgRating;
    private Integer reviewCount;
}

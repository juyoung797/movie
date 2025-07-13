package com.j797.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {
    private Integer id;
    private String title;
    private Integer releaseYear;
}

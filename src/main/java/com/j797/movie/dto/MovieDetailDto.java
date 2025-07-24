package com.j797.movie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieDetailDto {
    private Integer id;

    @NotBlank(message = "영화제목을 입력하세요")
    private String title;

    @NotNull(message = "개봉연도를 입력하세요")
    private Integer releasedYear;

    private Double avgRating;

    private Integer reviewCount;
}

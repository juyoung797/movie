package com.j797.movie.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {
    private Integer id;

    @NotNull(message = "영화 ID는 필수입니다.")
    private Integer movieId;

    @NotNull(message = "평점은 필수입니다.")
    @Min(value = 1, message = "평점은 최소 1점 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5점 이하여야 합니다.")
    private Integer rating;

    @Size(min = 5, max = 100, message = "리뷰는 최소 5자 이상, 최대 100자 이하여야 합니다.")
    private String comment;
}

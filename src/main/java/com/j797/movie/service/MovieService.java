package com.j797.movie.service;

import com.j797.movie.dto.MovieDetailDto;
import com.j797.movie.model.Movie;
import com.j797.movie.model.MovieRate;
import com.j797.movie.repository.MovieRateRepository;
import com.j797.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MovieService {
    public final MovieRepository movieRepository;
    public final MovieRateRepository movieRateRepository;

    public int create(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie getById(int id) {
        return movieRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 영화를 찾을 수 없습니다."));
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public MovieDetailDto getByIdWithRate(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("영화 없음"));

        MovieRate rate = movieRateRepository.findByMovieId(id)
                .orElseThrow(() -> new NoSuchElementException("평점 정보 없음"));

        return MovieDetailDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releasedYear(movie.getReleaseYear())
                .avgRating(rate.getAvgRating())
                .reviewCount(rate.getReviewCount())
                .build();
    }

    public List<MovieDetailDto> getAllWithRates() {
        List<Movie> movieList = movieRepository.findAll();
        List<MovieDetailDto> dtoList = new ArrayList<>();

        for (Movie movie : movieList) {
            MovieRate rate = movieRateRepository.findByMovieId(movie.getId())
                    .orElseThrow(() -> new NoSuchElementException("평점 정보 없음"));
            dtoList.add(MovieDetailDto.builder()
                    .id(movie.getId())
                    .title(movie.getTitle())
                    .releasedYear(movie.getReleaseYear())
                    .avgRating(rate.getAvgRating())
                    .reviewCount(rate.getReviewCount())
                    .build());
        }
        return dtoList;
    }

    public int update(Movie movie) {
        return movieRepository.update(movie);
    }

    public int delete(int id) {
        return movieRepository.deleteById(id);
    }
}

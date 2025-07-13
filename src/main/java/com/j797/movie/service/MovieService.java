package com.j797.movie.service;

import com.j797.movie.model.Movie;
import com.j797.movie.repository.MovieRepository;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MovieService {
    public final MovieRepository movieRepository;

    public int create(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie getById(int id) {
        return movieRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 영화를 찾을 수 없습니다."));
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public int update(Movie movie) {
        return movieRepository.update(movie);
    }

    public int delete(int id) {
        return movieRepository.deleteById(id);
    }
}

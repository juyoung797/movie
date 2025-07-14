package com.j797.movie.service;


import com.j797.movie.model.MovieRate;
import com.j797.movie.repository.MovieRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieRateService {
    public final MovieRateRepository movieRateRepository;

    public Optional<MovieRate> getByMovieId(int id) {
        return movieRateRepository.findByMovieId(id);
    }

    public List<MovieRate> getAllAvgRating() {
        return movieRateRepository.findAll();
    }

}

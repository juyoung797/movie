package com.j797.movie.service;


import com.j797.movie.model.Review;
import com.j797.movie.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewService {
    public final ReviewRepository reviewRepository;

    public int create(Review review) {
        return reviewRepository.save(review);
    }

    public Review getById(int id) {
        return reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다."));
    }

    public List<Review> getByMovieId(int id) {
        return reviewRepository.findByMovieId(id);
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public int update(Review review) {
        return reviewRepository.update(review);
    }

    public int delete(int id) {
        return reviewRepository.deleteById(id);
    }
}

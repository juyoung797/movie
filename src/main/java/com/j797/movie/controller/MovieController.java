package com.j797.movie.controller;

import com.j797.movie.dto.MovieDto;
import com.j797.movie.dto.MovieWithRateDto;
import com.j797.movie.model.Movie;
import com.j797.movie.model.MovieRate;
import com.j797.movie.model.Review;
import com.j797.movie.model.User;
import com.j797.movie.service.MovieRateService;
import com.j797.movie.service.MovieService;
import com.j797.movie.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MovieRateService movieRateService;
    private final ReviewService reviewService;

    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping
    public String list(Model model) {
        List<MovieWithRateDto> movies = movieService.getAllWithRates();
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        MovieWithRateDto movieWithRateDto = movieService.getByIdWithRate(id);
        List<Review> reviewList = reviewService.getByMovieId(id);

        model.addAttribute("movie", movieWithRateDto);
        model.addAttribute("reviewList", reviewList);
        return "movie-detail";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("movieDto", new MovieDto());
        return "movie-form";
    }

    @PostMapping("/add")
    public String add(
        @Valid @ModelAttribute MovieWithRateDto movieDto,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "movie-form";
        Movie movie = Movie.builder()
                .title(movieDto.getTitle())
                .releasedYear(movieDto.getReleasedYear())
                .build();

        movieService.create(movie);
        return "redirect:/movie";
    }
}

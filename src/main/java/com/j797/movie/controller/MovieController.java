package com.j797.movie.controller;

import com.j797.movie.dto.MovieDto;
import com.j797.movie.dto.MovieDetailDto;
import com.j797.movie.model.Movie;
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
        List<MovieDetailDto> movies = movieService.getAllWithRates();
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("movieDto", new MovieDto());
        return "movie-form";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute MovieDetailDto movieDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "movie-form";

        Movie movie = Movie.builder()
                .title(movieDto.getTitle())
                .releaseYear(movieDto.getReleasedYear())
                .build();

        movieService.create(movie);
        return "redirect:/movie";
    }

    @PostMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieService.getById(id));
        return "movie-form";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute MovieDto dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "movie-form";

        Movie movie = movieService.getById(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setReleaseYear(dto.getReleasedYear());
        movieService.update(movie);
        return "redirect:/movie";
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Integer id
    ) {
        movieService.delete(id);
        return "redirect:/movie";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        MovieDetailDto movieDetailDto = movieService.getByIdWithRate(id);
        List<Review> reviewList = reviewService.getByMovieId(id);

        model.addAttribute("movie", movieDetailDto);
        model.addAttribute("reviewList", reviewList);
        return "movie-detail";
    }
}
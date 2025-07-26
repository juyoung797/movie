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
    public String list(HttpSession session, Model model) {
        List<MovieDetailDto> movies = movieService.getAllWithRates();
        User user = getCurrentUser(session);
        model.addAttribute("movies", movies);
        model.addAttribute("user", user);
        return "movie-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("movieDto", new MovieDto());
        return "movie-form";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute MovieDto movieDto,
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

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Movie movie = movieService.getById(id);
        if (movie != null) {
            MovieDto movieDto = new MovieDto();
            movieDto.setId(movie.getId());
            movieDto.setTitle(movie.getTitle());
            movieDto.setReleasedYear(movie.getReleaseYear());
            model.addAttribute("movieDto", movieDto);
            return "movie-form";
        }
        return "redirect:/movie";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute MovieDto moviedto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "movie-form";

        Movie movie = movieService.getById(moviedto.getId());
        movie.setTitle(moviedto.getTitle());
        movie.setReleaseYear(moviedto.getReleasedYear());
        movieService.update(movie);
        return "redirect:/movie";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Integer id
    ) {
        movieService.delete(id);
        return "redirect:/movie";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model, HttpSession session) {
        MovieDetailDto movieDetailDto = movieService.getByIdWithRate(id);
        List<Review> reviewList = reviewService.getByMovieId(id);
        model.addAttribute("movieDetailDto", movieDetailDto);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("user", getCurrentUser(session));
        return "movie-detail";
    }
}
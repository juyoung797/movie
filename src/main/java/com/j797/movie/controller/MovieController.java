package com.j797.movie.controller;

import com.j797.movie.dto.MovieDto;
import com.j797.movie.model.Movie;
import com.j797.movie.model.User;
import com.j797.movie.service.MovieService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping
    public String list(Model model) {

        List<Movie> list = movieService.getAll();
        model.addAttribute("movies", list);
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
                .releasedYear(movieDto.getReleasedYear())
                .build();
        movieService.create(movie);
        return "redirect:/movie";
    }
}

package com.j797.movie.controller;

import com.j797.movie.dto.ReviewDto;
import com.j797.movie.model.Review;
import com.j797.movie.model.User;
import com.j797.movie.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/movie/{movieId}/review/add")
    public String addForm(
            @PathVariable Integer movieId,
            Model model
    ) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(movieId);
        model.addAttribute("reviewDto", reviewDto);
        return "review-form";
    }

    @PostMapping("/movie/{movieId}/review/add")
    public String add(
            @PathVariable Integer movieId,
            @Valid @ModelAttribute ReviewDto reviewDto,
            BindingResult bindingResult,
            HttpSession session
    ) {
        if (bindingResult.hasErrors()) return "review-form";

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Review review = Review.builder()
                .movieId(reviewDto.getMovieId())
                .userId(currentUser.getId())
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .reviewedAt(LocalDateTime.now())
                .build();

        reviewService.create(review);
        System.out.println("생성완료");
        return "redirect:/movie/detail/" + reviewDto.getMovieId();
    }

    @GetMapping("/review/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model
    ) {
        model.addAttribute("review", reviewService.getById(id));
        return "review-form";
    }

    @PostMapping("/review")
    public String edit(
            @Valid @ModelAttribute ReviewDto reviewdto,
            BindingResult bindingResult
    ) {
        return "redirect:/movie/detail/" + reviewdto.getMovieId();
    }

    @PostMapping("/review/{id}")
    public String delete(
            @PathVariable Integer id
    ) {
        reviewService.delete(id);
        return "redirect:/movie/detail/" + id;
    }
}

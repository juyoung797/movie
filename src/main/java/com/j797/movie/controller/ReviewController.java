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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping("/movie/{movieId}/review/add")
    public String addForm(
            HttpSession session,
            @PathVariable Integer movieId,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        User user = getCurrentUser(session);
        if (user == null) {
            redirectAttributes.addFlashAttribute("alertMsg", "로그인이 필요합니다.");
            return "redirect:/login";
        }

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
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) return "review-form";

        User user = getCurrentUser(session);
        if (user == null) {
            redirectAttributes.addFlashAttribute("alertMsg", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        Review review = Review.builder()
                .movieId(reviewDto.getMovieId())
                .userId(user.getId())
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .reviewedAt(LocalDateTime.now())
                .build();

        reviewService.create(review);
        return "redirect:/movie/detail/" + reviewDto.getMovieId();
    }

    @GetMapping("/review/{id}")
    public String editForm(
            @PathVariable Integer id,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Review review = reviewService.getById(id);
        if (!Objects.equals(review.getUserId(), getCurrentUser(session).getId())) {
            redirectAttributes.addFlashAttribute("alertMsg", "권한이 없습니다.");
            return "redirect:/movie/detail/" + id;
        }
        if (review != null) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(review.getId());
            reviewDto.setMovieId(review.getMovieId());
            reviewDto.setComment(review.getComment());
            reviewDto.setRating(review.getRating());
            model.addAttribute("reviewDto", reviewDto);
            return "review-form";
        }
        return "redirect:/movie/detail/" + id;
    }

    @PostMapping("/review")
    public String edit(
            @Valid @ModelAttribute ReviewDto reviewDto,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Review review = Review.builder()
                .id(reviewDto.getId())
                .movieId(reviewDto.getMovieId())
                .userId(getCurrentUser(session).getId())
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .build();
        reviewService.update(review);
        return "redirect:/movie/detail/" + reviewDto.getMovieId();
    }

    @PostMapping("/review/{id}")
    public String delete(
            @PathVariable Integer id
    ) {
        Integer movieId = reviewService.getById(id).getMovieId();
        reviewService.delete(id);
        return "redirect:/movie/detail/" + movieId;
    }
}

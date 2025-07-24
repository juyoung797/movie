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
@RequestMapping("/movie")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{movieId}/review/add")
    public String addForm(@PathVariable Integer movieId, Model model) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(movieId);  // 필수값 세팅
        model.addAttribute("reviewDto", reviewDto);
        return "review-form";
    }

    @PostMapping("/{movieId}/review/add")
    public String add(
            @PathVariable Integer movieId,
            @Valid @ModelAttribute ReviewDto reviewDto,
            BindingResult bindingResult,
            HttpSession session
    ) {
        System.out.println("바인딩에러 전");
        if (bindingResult.hasErrors()) return "review-form";
        System.out.println("바인딩에러 후");

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
}

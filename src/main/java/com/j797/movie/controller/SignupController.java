package com.j797.movie.controller;

import com.j797.movie.dto.SignupDto;
import com.j797.movie.model.User;
import com.j797.movie.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserService userService;

    @GetMapping("/signup")
    public String showSingup(Model model) {
        model.addAttribute("signupDto", new SignupDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(
            @Valid @ModelAttribute SignupDto SignupDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // 중복 가입 여부 체크
        if (userService.getByUsername((SignupDto.getUsername())) != null) {
            model.addAttribute("error", "이미 사용중인 아이디입니다");
            return "signup";
        }

        User user = User.builder()
                .username(SignupDto.getUsername())
                .password(SignupDto.getPassword())
                .build();

        userService.create(user);
        System.out.println("SignupDto is null? " + (SignupDto == null));

        return "redirect:/login?registered";
    }
}

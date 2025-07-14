package com.j797.movie.controller;

import com.j797.movie.dto.LoginDto;
import com.j797.movie.model.User;
import com.j797.movie.repository.UserRepository;
import com.j797.movie.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping({"/", "/login"})
    public String showLogin(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @Valid @ModelAttribute LoginDto loginDto,
            BindingResult bindingResult,
            HttpSession httpSession,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        Optional<User> userOpt = userService.getByUsername(loginDto.getUsername());

        if (userOpt.isEmpty()) {
            model.addAttribute("error", "존재하지 않는 사용자입니다.");
            return "login";
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(loginDto.getPassword())) {
            model.addAttribute("error", "비밀번호가 올바르지 않습니다");
            return "login";
        }

        httpSession.setAttribute("user", user);
        return "redirect:/movie";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
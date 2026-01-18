package com.erp.erp.controller;

import com.erp.erp.domain.User;
import com.erp.erp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;

    @GetMapping("/mypage")
    public String myPage(Authentication authentication, Model model) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        model.addAttribute("user", user);

        return "mypage/index";
    }
}

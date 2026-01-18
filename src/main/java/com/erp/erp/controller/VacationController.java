package com.erp.erp.controller;

import com.erp.erp.domain.*;
import com.erp.erp.repository.UserRepository;
import com.erp.erp.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vacation") // ⭐ 복수형 쓰지 마!
public class VacationController {

    private final VacationRepository vacationRepository;
    private final UserRepository userRepository;

    @GetMapping("/new")
    public String vacationForm() {
        return "vacation/form";
    }
    // ⭐ POST 경로 명확히
    @PostMapping
    public String submitVacation(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam String reason,
            Authentication authentication
    ) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Vacation vacation = Vacation.builder()
                .user(user)
                .startDate(startDate)
                .endDate(endDate)
                .reason(reason)
                .status(VacationStatus.PENDING) // ⭐ 필수
                .build();

        vacationRepository.save(vacation);

        return "redirect:/";
    }

}

package com.erp.erp.controller;

import com.erp.erp.domain.Vacation;
import com.erp.erp.domain.VacationStatus;
import com.erp.erp.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/vacations")
public class AdminVacationController {

    private final VacationRepository vacationRepository;

    // 승인 대기 목록
    @GetMapping
    public String vacationList(Model model) {

        model.addAttribute(
                "vacations",
                vacationRepository.findByStatus(VacationStatus.PENDING)

        );

        return "admin/vacation-list";
    }

    // 승인
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        Vacation v = vacationRepository.findById(id).orElseThrow();
        v.setStatus(VacationStatus.APPROVED);
        vacationRepository.save(v);
        return "redirect:/admin/vacations";
    }

    // 반려
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        Vacation v = vacationRepository.findById(id).orElseThrow();
        v.setStatus(VacationStatus.REJECTED);
        vacationRepository.save(v);
        return "redirect:/admin/vacations";
    }
}

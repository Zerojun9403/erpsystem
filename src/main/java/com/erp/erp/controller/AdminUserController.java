package com.erp.erp.controller;

import com.erp.erp.domain.User;
import com.erp.erp.domain.UserRole;
import com.erp.erp.domain.UserStatus;
import com.erp.erp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 📄 직원 목록
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/user-list";
    }

    // 📝 신규 등록 폼
    @GetMapping("/new")
    public String newUserForm() {
        return "admin/user-form";
    }

    // ✅ 신규 등록 처리
    @PostMapping
    public String createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.ROLE_EMPLOYEE);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        return "redirect:/admin/users";
    }

    // ❌ 직원 삭제 (물리 삭제)
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    // 📴 직원 퇴사 처리 (상태 변경)
    @PostMapping("/{id}/resign")
    public String resignUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        user.setStatus(UserStatus.RESIGNED);
        userRepository.save(user);

        return "redirect:/admin/users";
    }
}

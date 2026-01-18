package com.erp.erp.config;

import com.erp.erp.domain.User;
import com.erp.erp.domain.UserRole;
import com.erp.erp.domain.UserStatus;
import com.erp.erp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class InitAdminConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ⭐ 이게 핵심

    @PostConstruct
    public void initAdmin() {

        if (userRepository.findByEmail("admin@erp.com").isPresent()) {
            return;
        }

        User admin = User.builder()
                .email("admin@erp.com")
                .employeeNo("ADMIN001")
                .name("관리자")
                .password(passwordEncoder.encode("admin1234")) // ✅ 이제 인식됨
                .role(UserRole.ROLE_ADMIN)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(admin);
    }
}

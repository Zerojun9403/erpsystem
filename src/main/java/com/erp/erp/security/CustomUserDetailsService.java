package com.erp.erp.security;

import com.erp.erp.domain.User;
import com.erp.erp.domain.UserStatus;
import com.erp.erp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        // ⭐⭐ 퇴사자 로그인 차단 (여기가 핵심)
        if (user.getStatus() == UserStatus.RESIGNED) {
            throw new UsernameNotFoundException("퇴사한 사용자입니다");
        }
        // ⭐⭐ 퇴사자 로그인 금지(여기가 핵심)
        if (user.getStatus() == UserStatus.RESIGNED) {
            throw new DisabledException("퇴사한 사용자입니다.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}

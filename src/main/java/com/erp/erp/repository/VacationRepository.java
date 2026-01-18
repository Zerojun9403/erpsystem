package com.erp.erp.repository;

import com.erp.erp.domain.Vacation;
import com.erp.erp.domain.VacationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    // 관리자 승인 대기 목록
    List<Vacation> findByStatus(VacationStatus status);
}

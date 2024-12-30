package com.mozip.mozip.domain.user.repository;

import com.mozip.mozip.domain.user.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByPhoneAndCode(String phone, String code);
}

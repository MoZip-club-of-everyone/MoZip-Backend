package com.mozip.mozip.domain.user.repository;

import com.mozip.mozip.domain.user.entity.VerificationCode;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByPhoneAndCode(String phone, String code);
    List<VerificationCode> findAllByPhone(String phone);

    @Transactional
    void deleteByPhone(String phone);

    @Transactional
    void deleteByPhoneAndCode(String phone, String code);
}

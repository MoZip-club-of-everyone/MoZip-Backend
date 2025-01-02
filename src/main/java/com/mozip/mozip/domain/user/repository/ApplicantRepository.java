package com.mozip.mozip.domain.user.repository;

import com.mozip.mozip.domain.user.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, String> {
}

package com.mozip.mozip.domain.applicant.repository;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, String> {

    List<Applicant> findAllByMozipAndIsRegisteredTrue(Mozip mozip);

    Optional<Applicant> findTopByMozipOrderByApplicationNumberDesc(Mozip mozip);

    Optional<Applicant> findByUserAndMozip(User user, Mozip mozip);

    void deleteByIsRegisteredFalse();
}
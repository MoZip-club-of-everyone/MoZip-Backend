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

    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByUserRealnameAsc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByUserRealnameDesc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByCreatedAtAsc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByCreatedAtDesc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByPaperScoreAverageAsc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByPaperScoreAverageDesc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByInterviewScoreAverageAsc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByInterviewScoreAverageDesc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByApplicationNumberAsc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByApplicationNumberDesc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByPaperStatusAsc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByPaperStatusDesc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByInterviewStatusAsc(Mozip mozip);
    List<Applicant> findAllByMozipAndIsRegisteredTrueOrderByInterviewStatusDesc(Mozip mozip);
}
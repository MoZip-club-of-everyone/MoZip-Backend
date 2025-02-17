package com.mozip.mozip.domain.applicant.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.exception.ApplicantException;
import com.mozip.mozip.domain.applicant.repository.ApplicantRepository;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;

    public Applicant getApplicantById(String applicantId) {
        return applicantRepository.findById(applicantId)
                .orElseThrow(() -> ApplicantException.notFound(applicantId));
    }

    public List<Applicant> getApplicantsByMozip(Mozip mozip) {
        return applicantRepository.findAllByMozipAndIsRegisteredTrue(mozip);
    }

    public List<Applicant> getSortedApplicantsByMozip(Mozip mozip, String sortBy, String order) {
        switch (sortBy) {
            case "realname":
                return "desc".equalsIgnoreCase(order)
                        ? applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByUserRealnameDesc(mozip)
                        : applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByUserRealnameAsc(mozip);
            case "applied_at":
                return "desc".equalsIgnoreCase(order)
                        ? applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByCreatedAtDesc(mozip)
                        : applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByCreatedAtAsc(mozip);
            case "paper_score":
                return "desc".equalsIgnoreCase(order)
                        ? applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByPaperScoreAverageDesc(mozip)
                        : applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByPaperScoreAverageAsc(mozip);
            case "interview_score":
                return "desc".equalsIgnoreCase(order)
                        ? applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByInterviewScoreAverageDesc(mozip)
                        : applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByInterviewScoreAverageAsc(mozip);
            case "paper_status":
                return "desc".equalsIgnoreCase(order)
                        ? applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByPaperStatusDesc(mozip)
                        : applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByPaperStatusAsc(mozip);
            case "interview_status":
                return "desc".equalsIgnoreCase(order)
                        ? applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByInterviewStatusDesc(mozip)
                        : applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByInterviewStatusAsc(mozip);
            default:
                return "desc".equalsIgnoreCase(order)
                        ? applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByApplicationNumberDesc(mozip)
                        : applicantRepository.findAllByMozipAndIsRegisteredTrueOrderByApplicationNumberAsc(mozip);
        }
    }    

    public void saveApplicant(Applicant applicant) {
        applicantRepository.save(applicant);
    }

    public Long getApplicationNumberByMozip(Mozip mozip) {
        return applicantRepository.findTopByMozipOrderByApplicationNumberDesc(mozip)
                .map(Applicant::getApplicationNumber)
                .orElse(0L) + 1;
    }

    public Applicant getCurrentApplicant(User user, Mozip mozip) {
        Optional<Applicant> existingApplicant = applicantRepository.findByUserAndMozip(user, mozip);
        return existingApplicant.orElseGet(() -> createApplicant(user, mozip));
    }

    // 지원자 생성
    public Applicant createApplicant(User user, Mozip mozip) {
        Long applicationNumber = getApplicationNumberByMozip(mozip);
        Applicant applicant = Applicant.builder()
                .user(user)
                .mozip(mozip)
                .applicationNumber(applicationNumber)
                .build();
        return applicantRepository.save(applicant);
    }

    public void registerApplicant(Applicant applicant) {
        applicant.setIsRegistered(true);
        applicantRepository.save(applicant);
    }

    @Transactional
    public void deleteUnregisteredApplicants() {
        applicantRepository.deleteByIsRegisteredFalse();
    }
}
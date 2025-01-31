package com.mozip.mozip.domain.applicant.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.exception.ApplicantNotFoundException;
import com.mozip.mozip.domain.applicant.repository.ApplicantRepository;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;

    public Applicant getApplicantById(String applicantId) {
        return applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ApplicantNotFoundException(applicantId));
    }

    public List<Applicant> getApplicantsByMozip(Mozip mozip) {
        return applicantRepository.findAllByMozip(mozip);
    }

    public void saveApplicant(Applicant applicant) {
        applicantRepository.save(applicant);
    }

    public Long getApplicationNumberByMozip(Mozip mozip) {
        return applicantRepository.countAllByMozip(mozip) + 1;
    }

    // 지원자 생성
    public void createApplicant(User user, Mozip mozip) {
        Long applicationNumber = getApplicationNumberByMozip(mozip);
        Applicant applicant = Applicant.builder()
                .user(user)
                .mozip(mozip)
                .applicationNumber(applicationNumber)
                .build();
        applicantRepository.save(applicant);
    }

    // register true
    public void registerApplicant(Applicant applicant) {
        applicant.setIsRegistered(true);
        applicantRepository.save(applicant);
    }
}
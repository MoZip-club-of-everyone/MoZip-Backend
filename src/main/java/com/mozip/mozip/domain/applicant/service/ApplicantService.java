package com.mozip.mozip.domain.applicant.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.exception.ApplicantNotFoundException;
import com.mozip.mozip.domain.applicant.repository.ApplicantRepository;
import com.mozip.mozip.domain.mozip.entity.Mozip;
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
        return applicantRepository.findApplicantsByMozip(mozip);
    }

    public void saveApplicant(Applicant applicant) {
        applicantRepository.save(applicant);
    }
}
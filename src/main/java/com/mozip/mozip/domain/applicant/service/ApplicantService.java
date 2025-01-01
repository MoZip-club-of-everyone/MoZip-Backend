package com.mozip.mozip.domain.applicant.service;

import com.mozip.mozip.domain.applicant.dto.ApplicantListResponse;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.repository.ApplicantRepository;
import com.mozip.mozip.domain.applicant.entity.enums.ApplicationStatus;
import com.mozip.mozip.domain.club.entity.Mozip;
import com.mozip.mozip.domain.club.repository.MozipRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final ApplicantRepository applicantRepository;
    private final MozipRepository mozipRepository;

    public ApplicantListResponse getApplicantListByMozipId(String mozipId, String sortBy, String order) {
        Mozip mozip = mozipRepository.findById(mozipId)
                .orElseThrow(() -> new EntityNotFoundException("Mozip이 없습니다 : " + mozipId));

        List<Applicant> applicants = applicantRepository.findByMozip(mozip, sortBy, order);

        return ApplicantListResponse.from(applicants, mozip);
    }
}
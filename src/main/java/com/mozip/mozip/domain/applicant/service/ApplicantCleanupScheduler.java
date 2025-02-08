package com.mozip.mozip.domain.applicant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantCleanupScheduler {

    private final ApplicantService applicantService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpUnregisteredApplicants() {
        applicantService.deleteUnregisteredApplicants();
    }
} 
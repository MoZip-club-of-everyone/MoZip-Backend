package com.mozip.mozip.domain.user.controller;

import com.mozip.mozip.domain.user.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplicantController {
    private final ApplicantService applicantService;
}

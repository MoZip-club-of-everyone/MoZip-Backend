package com.mozip.mozip.domain.club.controller;

import com.mozip.mozip.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClubController {
    private final ClubService clubService;
}

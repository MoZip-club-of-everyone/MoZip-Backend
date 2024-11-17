package com.mozip.mozip.domain.club.service;

import com.mozip.mozip.domain.club.repository.ClubRepository;
import com.mozip.mozip.domain.club.repository.MozipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final MozipRepository mozipRepository;
}
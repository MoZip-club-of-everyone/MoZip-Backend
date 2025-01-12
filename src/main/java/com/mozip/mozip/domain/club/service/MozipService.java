package com.mozip.mozip.domain.club.service;

import com.mozip.mozip.domain.club.dto.MozipRequestDto;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.entity.Mozip;
import com.mozip.mozip.domain.club.repository.ClubRepository;
import com.mozip.mozip.domain.club.repository.MozipRepository;
import com.mozip.mozip.domain.question.dto.PaperQuestionCreateReqDto;
import com.mozip.mozip.domain.question.service.PaperQuestionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MozipService {

    private final MozipRepository mozipRepository;
    private final ClubRepository clubRepository;
    private final PaperQuestionService paperQuestionService;

    public List<Mozip> getMozipsByClubId(String clubId) {
        return mozipRepository.findByClubId(clubId);
    }

    public Mozip getMozipById(String mozipId) {
        return mozipRepository.findById(mozipId)
                .orElseThrow(() -> new EntityNotFoundException("Mozip이 없습니다 : " + mozipId));
    }

    @Transactional
    public Mozip createMozip(String clubId, MozipRequestDto dto) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("Club이 없습니다 : " + clubId));
        Mozip mozip = Mozip.builder()
                .club(club)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .paperQuestions(dto.getPaperQuestions().stream().map(paperQuestionService::dtoToEntity).toList())
                .build();
        return mozipRepository.save(mozip);
    }

    @Transactional
    public Mozip updateMozip(String mozipId, String title, String description) {
        Mozip mozip = getMozipById(mozipId);
        mozip.setTitle(title);
        mozip.setDescription(description);
        return mozipRepository.save(mozip);
    }

    @Transactional
    public void deleteMozip(String mozipId) {
        Mozip mozip = getMozipById(mozipId);
        mozipRepository.delete(mozip);
    }
}
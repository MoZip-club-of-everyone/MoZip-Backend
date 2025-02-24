package com.mozip.mozip.domain.mozip.service;

import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.club.service.ClubService;
import com.mozip.mozip.domain.mozip.dto.MozipRequestDto;
import com.mozip.mozip.domain.mozip.dto.MozipResponseDto;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.mozip.entity.enums.MozipStatus;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.paperQuestion.service.PaperQuestionService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MozipManager {
    private final MozipService mozipService;
    private final PaperQuestionService paperQuestionService;
    private final ClubService clubService;

    public List<MozipResponseDto> getMozipsByClubId(String clubId) {
        List<Mozip> mozips = mozipService.getMozipsByClubId(clubId);
        return mozips.stream().map(MozipResponseDto::entityToDto).toList();
    }

    public MozipResponseDto getMozipInfoById(String mozipId) {
        Mozip mozip = mozipService.getMozipById(mozipId);
        return MozipResponseDto.entityToDto(mozip);
    }

    @Transactional
    public MozipResponseDto createMozip(String clubId, MozipRequestDto dto) {
        Club club = clubService.getClubById(clubId);
        Mozip createdMozip = mozipService.createMozip(club, dto);
        List<PaperQuestion> paperQuestions = dto.getPaperQuestions()
                .stream().map(paperQuestion -> paperQuestionService.createPaperQuestion(createdMozip, paperQuestion))
                .toList();
        Mozip updatedMozip = mozipService.updateMozipQuestions(createdMozip, paperQuestions);
        return MozipResponseDto.entityToDto(updatedMozip);
    }

    @Transactional
    public MozipResponseDto updateMozip(String mozipId, String title, String description) {
        Mozip updatedMozip = mozipService.updateMozip(mozipId, title, description);
        return MozipResponseDto.entityToDto(updatedMozip);
    }

    @Transactional
    public MozipResponseDto updateMozipStatus(String mozipId, MozipStatus status) {
        Mozip updatedMozip = mozipService.updateMozipStatus(mozipId, status);
        return MozipResponseDto.entityToDto(updatedMozip);
    }

    @Transactional
    public void deleteMozip(String mozipId) {
        mozipService.deleteMozip(mozipId);
    }
}
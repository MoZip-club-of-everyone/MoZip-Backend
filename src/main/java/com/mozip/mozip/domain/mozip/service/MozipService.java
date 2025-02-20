package com.mozip.mozip.domain.mozip.service;

import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.mozip.dto.MozipRequestDto;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.mozip.repository.MozipRepository;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MozipService {

    private final MozipRepository mozipRepository;

    public List<Mozip> getMozipsByClubId(String clubId) {
        return mozipRepository.findByClubId(clubId);
    }

    public Mozip getMozipById(String mozipId) {
        return mozipRepository.findById(mozipId)
                .orElseThrow(() -> new EntityNotFoundException("Mozip이 없습니다 : " + mozipId));
    }

    @Transactional
    public Mozip createMozip(Club club, MozipRequestDto dto) {
        Mozip mozip = Mozip.builder()
                .club(club)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isLoginRequired(dto.getIsLoginRequired())
                .isEditAvailable(dto.getIsEditAvailable())
                .descriptionBeforeMozip(dto.getDescriptionBeforeMozip())
                .descriptionAfterMozip(dto.getDescriptionAfterMozip())
                .paperQuestions(new ArrayList<>())
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
    public Mozip updateMozipQuestions(Mozip mozip, List<PaperQuestion> paperQuestions) {
        mozip.setPaperQuestions(new ArrayList<>(paperQuestions));
        return mozipRepository.save(mozip);
    }

    @Transactional
    public void deleteMozip(String mozipId) {
        Mozip mozip = getMozipById(mozipId);
        mozipRepository.delete(mozip);
    }
}
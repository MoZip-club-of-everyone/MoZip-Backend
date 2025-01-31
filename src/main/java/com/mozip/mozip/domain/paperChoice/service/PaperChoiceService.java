package com.mozip.mozip.domain.paperChoice.service;

import com.mozip.mozip.domain.paperChoice.entity.PaperChoice;
import com.mozip.mozip.domain.paperChoice.repository.PaperChoiceRepository;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperChoiceService {
    private final PaperChoiceRepository paperChoiceRepository;

    @Transactional
    public PaperChoice createPaperChoice(PaperQuestion paperQuestion, String choice) {
        PaperChoice paperChoice = PaperChoice.builder()
                .paperQuestion(paperQuestion)
                .choice(choice)
                .build();
        return paperChoiceRepository.save(paperChoice);
    }

    public PaperChoice getPaperChoiceById(String id) {
        return paperChoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PaperChoice가 발견되지 않았습니다. id: " + id));
    }

    public List<PaperChoice> getPaperChoiceByPaperQuestion(PaperQuestion paperQuestion) {
        return paperChoiceRepository.findByPaperQuestionId(paperQuestion.getId());
    }

    @Transactional
    public PaperChoice updatePaperChoice(String id, String choice) {
        PaperChoice paperChoice = getPaperChoiceById(id);
        paperChoice.setChoice(choice);
        return paperChoiceRepository.save(paperChoice);
    }

    @Transactional
    public void deletePaperChoice(String id) {
        paperChoiceRepository.deleteById(id);
    }
}
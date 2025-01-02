package com.mozip.mozip.domain.question.service;

import com.mozip.mozip.domain.club.entity.Mozip;
import com.mozip.mozip.domain.club.repository.MozipRepository;
import com.mozip.mozip.domain.question.dto.PaperQuestionCreateReqDto;
import com.mozip.mozip.domain.question.dto.PaperQuestionUpdateReqDto;
import com.mozip.mozip.domain.question.entity.PaperQuestion;
import com.mozip.mozip.domain.question.repository.PaperQuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperQuestionService {

    private final PaperQuestionRepository questionRepository;
    private final MozipRepository mozipRepository;

    public List<PaperQuestion> getPaperQuestionsByMozipId(String mozipId) {
        return questionRepository.findByMozipId(mozipId);
    }

    public PaperQuestion getPaperQuestionById(String questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("PaperQuestion이 없습니다 : " + questionId));
    }

    @Transactional
    public PaperQuestion createPaperQuestion(PaperQuestionCreateReqDto requestDto) {
        Mozip mozip = mozipRepository.findById(requestDto.getMozipId())
                .orElseThrow(() -> new EntityNotFoundException("Mozip이 없습니다 : " + requestDto.getMozipId()));

        PaperQuestion paperQuestion = PaperQuestion.builder()
                .mozip(mozip)
                .question(requestDto.getQuestion())
                .details(requestDto.getDetails())
                .isRequired(requestDto.isRequired())
                .build();
        return questionRepository.save(paperQuestion);
    }

    @Transactional
    public PaperQuestion updatePaperQuestion(String questionId, PaperQuestionUpdateReqDto requestDto) {
        PaperQuestion question = getPaperQuestionById(questionId);
        question.updateQuestion(requestDto.getQuestion(), requestDto.getDetails(), requestDto.isRequired());
        return questionRepository.save(question);
    }

    @Transactional
    public void deletePaperQuestion(String questionId) {
        PaperQuestion existingQuestion = getPaperQuestionById(questionId);
        questionRepository.delete(existingQuestion);
    }
}
package com.mozip.mozip.domain.paperQuestion.service;

import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionCreateReqDto;
import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionUpdateReqDto;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.paperQuestion.repository.PaperQuestionRepository;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperQuestionService {

    private final PaperQuestionRepository questionRepository;

    public List<PaperQuestion> getPaperQuestionsByMozipId(String mozipId) {
        return questionRepository.findByMozipId(mozipId);
    }

    public PaperQuestion getPaperQuestionById(String questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("PaperQuestion이 없습니다 : " + questionId));
    }

    @Transactional
    public PaperQuestion createPaperQuestion(Mozip mozip, PaperQuestionCreateReqDto requestDto) {
        PaperQuestion paperQuestion = PaperQuestion.builder()
                .mozip(mozip)
                .question(requestDto.getQuestion())
                .type(requestDto.getType())
                .details(requestDto.getDetails())
                .isRequired(requestDto.isRequired())
                .build();
        return questionRepository.save(paperQuestion);
    }

    @Transactional
    public PaperQuestion updatePaperQuestion(String questionId, PaperQuestionUpdateReqDto requestDto) {
        PaperQuestion question = getPaperQuestionById(questionId);
        question.updateQuestion(requestDto.getQuestion(), requestDto.getDetails(), requestDto.getType(), requestDto.isRequired());
        return questionRepository.save(question);
    }

    @Transactional
    public void deletePaperQuestion(String questionId) {
        PaperQuestion existingQuestion = getPaperQuestionById(questionId);
        questionRepository.delete(existingQuestion);
    }

    public PaperQuestion dtoToEntity(Mozip mozip, PaperQuestionCreateReqDto requestDto){
        return PaperQuestion.builder()
                .mozip(mozip)
                .question(requestDto.getQuestion())
                .details(requestDto.getDetails())
                .isRequired(requestDto.isRequired())
                .build();
    }

    public List<PaperQuestion> getPaperQuestionsByMozipOrQuestionIds(Mozip mozip, List<String> questionIds) {
        if (questionIds == null) {
            return questionRepository.findByMozipId(mozip.getId());
        }
        return questionRepository.findByIdIn(questionIds);
    }
}
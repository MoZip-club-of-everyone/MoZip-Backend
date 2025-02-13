package com.mozip.mozip.domain.paperAnswer.service;

import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperAnswer.repository.PaperAnswerRepository;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaperAnswerService {

    private final PaperAnswerRepository paperAnswerRepository;

    // 지원자에 대한 모든 답변 조회
    public List<PaperAnswer> getPaperAnswersByApplicantIdAndMozipId(String applicantId, String mozipId) {
        return paperAnswerRepository.findByApplicantIdAndPaperQuestionMozipId(applicantId, mozipId);
    }

    // 특정 질문에 대한 모든 답변 조회
    public List<PaperAnswer> getPaperAnswersByQuestionId(String paperQuestionId) {
        return paperAnswerRepository.findByPaperQuestionId(paperQuestionId);
    }

    // 지원자의 특정 질문에 대한 답변 조회
    public Optional<PaperAnswer> getPaperAnswerByQuestionAndApplicant(String paperQuestionId, String applicantId) {
        return paperAnswerRepository.findByPaperQuestionIdAndApplicantId(paperQuestionId, applicantId);
    }

    // id로 답변 조회
    public PaperAnswer getPaperAnswerById(String answerId) {
        return paperAnswerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer가 없습니다 : " + answerId));
    }

    @Transactional
    public PaperAnswer createAnswer(Applicant applicant, PaperQuestion paperQuestion, String answer) {
        PaperAnswer paperAnswer = getPaperAnswerByQuestionAndApplicant(paperQuestion.getId(), applicant.getId())
                .map(existingAnswer -> {
                    existingAnswer.setAnswer(answer);
                    return existingAnswer;
                })
                .orElseGet(() -> PaperAnswer.builder()
                        .applicant(applicant)
                        .paperQuestion(paperQuestion)
                        .answer(answer)
                        .build());
        return  paperAnswerRepository.save(paperAnswer);
    }

    @Transactional
    public PaperAnswer updateAnswer(String answerId, String answer) {
        PaperAnswer existingAnswer = getPaperAnswerById(answerId);
        existingAnswer.setAnswer(answer);
        return paperAnswerRepository.save(existingAnswer);
    }

    @Transactional
    public void deleteAnswer(String answerId) {
        PaperAnswer existingAnswer = getPaperAnswerById(answerId);
        paperAnswerRepository.delete(existingAnswer);
    }
}
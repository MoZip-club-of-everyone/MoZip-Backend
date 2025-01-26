package com.mozip.mozip.domain.answer.service;

import com.mozip.mozip.domain.answer.dto.PaperAnswerCreateReqDto;
import com.mozip.mozip.domain.answer.dto.PaperAnswerUpdateReqDto;
import com.mozip.mozip.domain.answer.entity.PaperAnswer;
import com.mozip.mozip.domain.answer.repository.PaperAnswerRepository;
import com.mozip.mozip.domain.PaperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.PaperQuestion.repository.PaperQuestionRepository;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.repository.ApplicantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaperAnswerService {

    private final PaperAnswerRepository paperAnswerRepository;
    private final ApplicantRepository applicantRepository;
    private final PaperQuestionRepository paperQuestionRepository;

    public List<PaperAnswer> getAnswersByApplicantId(String applicantId) {
        return paperAnswerRepository.findByApplicantId(applicantId);
    }

    public PaperAnswer getAnswerById(String answerId) {
        return paperAnswerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer가 없습니다 : " + answerId));
    }

    @Transactional
    public PaperAnswer createAnswer(PaperAnswerCreateReqDto requestDto) {
        Applicant applicant = applicantRepository.findById(requestDto.getApplicantId())
                .orElseThrow(() -> new EntityNotFoundException("Applicant이 없습니다 : " + requestDto.getApplicantId()));
        PaperQuestion question = paperQuestionRepository.findById(requestDto.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Question이 없습니다 : " + requestDto.getQuestionId()));

        PaperAnswer paperAnswer = PaperAnswer.builder()
                .applicant(applicant)
                .question(question)
                .answer(requestDto.getAnswer())
                .build();
        return paperAnswerRepository.save(paperAnswer);
    }

    @Transactional
    public PaperAnswer updateAnswer(String answerId, PaperAnswerUpdateReqDto requestDto) {
        PaperAnswer existingAnswer = getAnswerById(answerId);
        existingAnswer.updateAnswer(requestDto.getAnswer());
        return paperAnswerRepository.save(existingAnswer);
    }

    @Transactional
    public void deleteAnswer(String answerId) {
        PaperAnswer existingAnswer = getAnswerById(answerId);
        paperAnswerRepository.delete(existingAnswer);
    }
}
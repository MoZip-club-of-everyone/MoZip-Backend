package com.mozip.mozip.domain.paperAnswer.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.service.ApplicantService;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerCreateReqDto;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerResDto;
import com.mozip.mozip.domain.paperAnswer.dto.PaperAnswerUpdateReqDto;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.domain.paperQuestion.service.PaperQuestionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaperAnswerManager {

    private final PaperAnswerService paperAnswerService;
    private final ApplicantService applicantService;
    private final PaperQuestionService paperQuestionService;


    // 지원자에 대한 모든 답변 조회
    public List<PaperAnswerResDto> getPaperAnswersByApplicantIdAndMozipId(String applicantId, String mozipId) {
        return paperAnswerService.getPaperAnswersByApplicantIdAndMozipId(applicantId, mozipId)
                .stream().map(PaperAnswerResDto::fromEntity).toList();
    }

    // 특정 질문에 대한 모든 답변 조회
    public List<PaperAnswerResDto> getPaperAnswersByQuestionId(String paperQuestionId) {
        return paperAnswerService.getPaperAnswersByQuestionId(paperQuestionId)
                .stream().map(PaperAnswerResDto::fromEntity).toList();
    }

    // 지원자의 특정 질문에 대한 답변 조회
    public PaperAnswerResDto getPaperAnswerByQuestionAndApplicant(String paperQuestionId, String applicantId) {
        PaperAnswer paperAnswer = paperAnswerService.getPaperAnswerByQuestionAndApplicant(paperQuestionId, applicantId)
                .orElseThrow(() -> new EntityNotFoundException("PaperAnswer가 없습니다 : " + paperQuestionId + ", " + applicantId));
        return PaperAnswerResDto.fromEntity(paperAnswer);
    }

    // id로 답변 조회
    public PaperAnswerResDto getPaperAnswerById(String answerId) {
        return PaperAnswerResDto.fromEntity(paperAnswerService.getPaperAnswerById(answerId));
    }

    @Transactional
    public PaperAnswerResDto createAnswer(PaperAnswerCreateReqDto requestDto) {
        Applicant applicant = applicantService.getApplicantById(requestDto.getApplicantId());
        PaperQuestion paperQuestion = paperQuestionService.getPaperQuestionById(requestDto.getQuestionId());
        return PaperAnswerResDto.fromEntity(paperAnswerService.createAnswer(applicant, paperQuestion, requestDto.getAnswer()));
    }

    @Transactional
    public void registerAnswer(String applicantId) {
        Applicant applicant = applicantService.getApplicantById(applicantId);
        applicantService.registerApplicant(applicant);
    }

    @Transactional
    public PaperAnswerResDto updateAnswer(String answerId, PaperAnswerUpdateReqDto requestDto) {
        return PaperAnswerResDto.fromEntity(paperAnswerService.updateAnswer(answerId, requestDto.getAnswer()));
    }

    @Transactional
    public void deleteAnswer(String answerId) {
        paperAnswerService.deleteAnswer(answerId);
    }
}
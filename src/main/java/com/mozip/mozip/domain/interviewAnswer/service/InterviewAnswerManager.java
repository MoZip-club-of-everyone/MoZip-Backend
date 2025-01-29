package com.mozip.mozip.domain.interviewAnswer.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.service.ApplicantService;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswerCreateReqDto;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswerResDto;
import com.mozip.mozip.domain.interviewAnswer.dto.InterviewAnswerUpdateReqDto;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import com.mozip.mozip.domain.interviewQuestion.service.InterviewQuestionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewAnswerManager {

    private final InterviewAnswerService interviewAnswerService;
    private final ApplicantService applicantService;
    private final InterviewQuestionService interviewQuestionService;


    // 지원자에 대한 모든 답변 조회
    public List<InterviewAnswerResDto> getInterviewAnswersByApplicantId(String applicantId) {
        return interviewAnswerService.getInterviewAnswersByApplicantId(applicantId)
                .stream().map(InterviewAnswerResDto::fromEntity).toList();
    }

    // 특정 질문에 대한 모든 답변 조회
    public List<InterviewAnswerResDto> getInterviewAnswersByQuestionId(String paperQuestionId) {
        return interviewAnswerService.getInterviewAnswersByQuestionId(paperQuestionId)
                .stream().map(InterviewAnswerResDto::fromEntity).toList();
    }

    // 지원자의 특정 질문에 대한 답변 조회
    public InterviewAnswerResDto getInterviewAnswerByQuestionAndApplicant(String paperQuestionId, String applicantId) {
        InterviewAnswer interviewAnswer = interviewAnswerService.getInterviewAnswerByQuestionAndApplicant(paperQuestionId, applicantId)
                .orElseThrow(() -> new EntityNotFoundException("InterviewAnswer가 없습니다 : " + paperQuestionId + ", " + applicantId));
        return InterviewAnswerResDto.fromEntity(interviewAnswer);
    }

    // id로 답변 조회
    public InterviewAnswerResDto getInterviewAnswerById(String answerId) {
        return InterviewAnswerResDto.fromEntity(interviewAnswerService.getInterviewAnswerById(answerId));
    }

    @Transactional
    public InterviewAnswerResDto createAnswer(InterviewAnswerCreateReqDto requestDto) {
        Applicant applicant = applicantService.getApplicantById(requestDto.getApplicantId());
        InterviewQuestion interviewQuestion = interviewQuestionService.getInterviewQuestionById(requestDto.getQuestionId());
        return InterviewAnswerResDto.fromEntity(
                interviewAnswerService.createAnswer(applicant, interviewQuestion, requestDto.getAnswer()));
    }

    @Transactional
    public InterviewAnswerResDto updateAnswer(String answerId, InterviewAnswerUpdateReqDto requestDto) {
        return InterviewAnswerResDto.fromEntity(interviewAnswerService.updateAnswer(answerId, requestDto.getAnswer()));
    }

    @Transactional
    public void deleteAnswer(String answerId) {
        interviewAnswerService.deleteAnswer(answerId);
    }
}
package com.mozip.mozip.domain.interviewAnswer.service;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.interviewAnswer.repository.InterviewAnswerRepository;
import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewAnswerService {

    private final InterviewAnswerRepository interviewAnswerRepository;

    // 지원자에 대한 모든 답변 조회
    public List<InterviewAnswer> getInterviewAnswersByApplicantId(String applicantId) {
        return interviewAnswerRepository.findByApplicantId(applicantId);
    }

    // 특정 질문에 대한 모든 답변 조회
    public List<InterviewAnswer> getInterviewAnswersByQuestionId(String paperQuestionId) {
        return interviewAnswerRepository.findByInterviewQuestionId(paperQuestionId);
    }

    // 지원자의 특정 질문에 대한 답변 조회
    public Optional<InterviewAnswer> getInterviewAnswerByQuestionAndApplicant(String paperQuestionId, String applicantId) {
        return interviewAnswerRepository.findByInterviewQuestionIdAndApplicantId(paperQuestionId, applicantId);
    }

    // id로 답변 조회
    public InterviewAnswer getInterviewAnswerById(String answerId) {
        return interviewAnswerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer가 없습니다 : " + answerId));
    }

    @Transactional
    public InterviewAnswer createAnswer(Applicant applicant, InterviewQuestion interviewQuestion, String answer) {
        getInterviewAnswerByQuestionAndApplicant(interviewQuestion.getId(), applicant.getId())
                .ifPresent(interviewAnswerRepository::delete);

        InterviewAnswer interviewAnswer = InterviewAnswer.builder()
                .applicant(applicant)
                .interviewQuestion(interviewQuestion)
                .answer(answer)
                .build();
        return interviewAnswerRepository.save(interviewAnswer);
    }

    @Transactional
    public InterviewAnswer updateAnswer(String answerId, String answer) {
        InterviewAnswer existingAnswer = getInterviewAnswerById(answerId);
        existingAnswer.setAnswer(answer);
        return interviewAnswerRepository.save(existingAnswer);
    }

    @Transactional
    public void deleteAnswer(String answerId) {
        InterviewAnswer existingAnswer = getInterviewAnswerById(answerId);
        interviewAnswerRepository.delete(existingAnswer);
    }
}
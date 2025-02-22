package com.mozip.mozip.domain.interviewAnswer.repository;

import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewAnswerRepository extends JpaRepository<InterviewAnswer, String> {
    List<InterviewAnswer> findByApplicantId(String applicantId);
    List<InterviewAnswer> findByInterviewQuestionId(String interviewQuestionId);
    Optional<InterviewAnswer> findByInterviewQuestionIdAndApplicantId(String interviewQuestionId, String applicantId);
    List<InterviewAnswer> findByInterviewQuestionIdAndApplicantIdIn(String interviewQuestionId, List<String> applicantIds);
}

package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.evaluation.entity.InterviewComment;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewCommentRepository extends JpaRepository<InterviewComment, String> {
    List<InterviewComment> findByInterviewAnswer(InterviewAnswer interviewAnswer);
}

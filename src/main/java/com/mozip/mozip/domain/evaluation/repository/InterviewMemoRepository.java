package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.evaluation.entity.InterviewMemo;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewMemoRepository extends JpaRepository<InterviewMemo, String> {
    List<InterviewMemo> findByInterviewAnswer(InterviewAnswer interviewAnswer);
} 
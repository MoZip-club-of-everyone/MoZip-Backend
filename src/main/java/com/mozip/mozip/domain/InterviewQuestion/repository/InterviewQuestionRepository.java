package com.mozip.mozip.domain.InterviewQuestion.repository;

import com.mozip.mozip.domain.InterviewQuestion.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, String> {
}

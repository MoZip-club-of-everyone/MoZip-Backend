package com.mozip.mozip.domain.question.repository;

import com.mozip.mozip.domain.question.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, String> {
}

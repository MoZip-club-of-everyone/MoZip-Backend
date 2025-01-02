package com.mozip.mozip.domain.answer.repository;

import com.mozip.mozip.domain.answer.entity.InterviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewAnswerRepository extends JpaRepository<InterviewAnswer, String> {
}

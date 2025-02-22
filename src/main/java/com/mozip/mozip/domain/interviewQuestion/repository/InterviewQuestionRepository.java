package com.mozip.mozip.domain.interviewQuestion.repository;

import com.mozip.mozip.domain.interviewQuestion.entity.InterviewQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, String> {
    List<InterviewQuestion> findByMozipId(String mozipId);
    List<InterviewQuestion> findByIdIn(List<String> questionIds);
}
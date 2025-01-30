package com.mozip.mozip.domain.paperAnswer.repository;

import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperAnswerRepository extends JpaRepository<PaperAnswer, String> {
    List<PaperAnswer> findByApplicantId(String applicantId);
    List<PaperAnswer> findByPaperQuestionId(String paperQuestionId);
    Optional<PaperAnswer> findByPaperQuestionIdAndApplicantId(String paperQuestionId, String applicantId);
}

package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.answer.entity.PaperAnswer;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.evaluation.entity.PaperEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaperEvaluationRepository extends JpaRepository<PaperEvaluation, Long> {
    Optional<PaperEvaluation> findByPaperAnswerAndEvaluation(PaperAnswer paperAnswer, Evaluation evaluation);
}

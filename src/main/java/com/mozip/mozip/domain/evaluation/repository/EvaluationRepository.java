package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
    List<Evaluation> findByApplicant(Applicant applicant);
    Optional<Evaluation> findByApplicantAndEvaluator(Applicant applicant, User evaluator);
}
package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
    List<Evaluation> findByApplicant(Applicant applicant);
    Optional<Evaluation> findByApplicantAndEvaluator(Applicant applicant, User evaluator);
    long countByApplicantAndPaperScoreIsNotNull(Applicant applicant);

    @Query("SELECT AVG(e.paperScore) FROM Evaluation e WHERE e.applicant = :applicant AND e.paperScore IS NOT NULL")
    Double calculateAveragePaperScoreByApplicant(@Param("applicant") Applicant applicant);

    @Query("SELECT AVG(e.interviewScore) FROM Evaluation e WHERE e.applicant = :applicant AND e.interviewScore IS NOT NULL")
    Double calculateAverageInterviewScoreByApplicant(@Param("applicant") Applicant applicant);

    @Query("SELECT STDDEV_POP(e.paperScore) FROM Evaluation e WHERE e.applicant = :applicant AND e.paperScore IS NOT NULL")
    Double calculatePaperScoreStandardDeviationByApplicant(@Param("applicant") Applicant applicant);

    @Query("SELECT STDDEV_POP(e.interviewScore) FROM Evaluation e WHERE e.applicant = :applicant AND e.interviewScore IS NOT NULL")
    Double calculateInterviewScoreStandardDeviationByApplicant(@Param("applicant") Applicant applicant);

}
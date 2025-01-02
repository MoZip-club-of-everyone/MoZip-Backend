package com.mozip.mozip.domain.answer.repository;

import com.mozip.mozip.domain.answer.entity.PaperAnswer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperAnswerRepository extends JpaRepository<PaperAnswer, String> {
    List<PaperAnswer> findByApplicantId(String applicantId);
}

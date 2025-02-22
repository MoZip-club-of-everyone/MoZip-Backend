package com.mozip.mozip.domain.paperQuestion.repository;

import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperQuestionRepository extends JpaRepository<PaperQuestion, String> {
    List<PaperQuestion> findByMozipId(String mozipId);
    List<PaperQuestion> findByIdIn(List<String> questionIds);
}
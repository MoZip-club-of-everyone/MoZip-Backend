package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.evaluation.entity.PaperComment;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaperCommentRepository extends JpaRepository<PaperComment, String> {
    List<PaperComment> findByPaperAnswer(PaperAnswer paperAnswer);
}

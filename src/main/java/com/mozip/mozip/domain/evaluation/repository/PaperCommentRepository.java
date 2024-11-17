package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.evaluation.entity.PaperComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperCommentRepository extends JpaRepository<PaperComment, String> {
}

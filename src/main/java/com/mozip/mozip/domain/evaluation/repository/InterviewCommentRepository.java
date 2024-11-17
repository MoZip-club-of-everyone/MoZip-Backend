package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.evaluation.entity.InterviewComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewCommentRepository extends JpaRepository<InterviewComment, String> {
}

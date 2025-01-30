package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.evaluation.entity.PaperMemo;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaperMemoRepository extends JpaRepository<PaperMemo, String> {
    List<PaperMemo> findByPaperAnswer(PaperAnswer paperAnswer);
} 
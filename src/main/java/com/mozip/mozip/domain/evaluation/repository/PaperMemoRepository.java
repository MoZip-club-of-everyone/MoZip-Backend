package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.evaluation.entity.PaperMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperMemoRepository extends JpaRepository<PaperMemo, String> {
    // 필요한 경우 커스텀 쿼리 메서드를 추가할 수 있습니다.
} 
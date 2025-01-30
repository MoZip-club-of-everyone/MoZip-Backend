package com.mozip.mozip.domain.evaluation.repository;

import com.mozip.mozip.domain.evaluation.entity.InterviewMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewMemoRepository extends JpaRepository<InterviewMemo, String> {
    // 필요한 경우 커스텀 쿼리 메서드를 추가할 수 있습니다.
} 
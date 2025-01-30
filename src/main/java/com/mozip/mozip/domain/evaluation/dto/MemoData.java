package com.mozip.mozip.domain.evaluation.dto;

import com.mozip.mozip.domain.evaluation.entity.InterviewMemo;
import com.mozip.mozip.domain.evaluation.entity.PaperMemo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemoData {
    private String memo;
    private String writer;
    private LocalDateTime date;

    public static MemoData from(PaperMemo memo) {
        return MemoData.builder()
                .memo(memo.getMemo())
                .writer(memo.getWriter().getRealname())
                .date(memo.getUpdatedAt())
                .build();
    }

    public static MemoData from(InterviewMemo memo) {
        return MemoData.builder()
                .memo(memo.getMemo())
                .writer(memo.getWriter().getRealname())
                .date(memo.getUpdatedAt())
                .build();
    }
} 
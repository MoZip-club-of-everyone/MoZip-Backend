package com.mozip.mozip.domain.club.dto;

import com.mozip.mozip.domain.question.dto.PaperQuestionCreateReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MozipRequestDto {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<PaperQuestionCreateReqDto> paperQuestions;
}
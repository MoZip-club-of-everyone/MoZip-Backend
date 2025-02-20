package com.mozip.mozip.domain.mozip.dto;

import com.mozip.mozip.domain.paperQuestion.dto.PaperQuestionCreateReqDto;
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
    private Boolean isLoginRequired;
    private Boolean isEditAvailable;
    private String descriptionBeforeMozip;
    private String descriptionAfterMozip;
    private List<PaperQuestionCreateReqDto> paperQuestions;
}
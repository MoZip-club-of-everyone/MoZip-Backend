package com.mozip.mozip.domain.applicant.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateApplicantStatusResponse {
    @Builder.Default
    private String message = "지원자 상태가 성공적으로 업데이트되었습니다.";
    private String timestamp;
}
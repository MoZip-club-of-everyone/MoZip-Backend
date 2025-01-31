package com.mozip.mozip.domain.applicant.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.applicant.entity.Applicant;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public abstract class ApplicantData {
    private String applicantId;
    private Long applicationNumber;
    private String realname;
    private LocalDateTime appliedAt;
    private String email;
    private String phone;

    // 하위 클래스에서 각자 구현해야 할 메서드
    public abstract ApplicantData withStatus(Applicant applicant);

    // 상태를 반환하는 추상 메서드
    public abstract EvaluationStatus getStatus();
}
package com.mozip.mozip.domain.applicant.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum EvaluationStatus {
    PASSED("합격"), // 합격
    FAILED("불합격"), // 불합격
    WAITLISTED("예비"), // 예비
    HOLD("평가 제외"), // 보류 -> 평가 제외
    DEFAULT("-");

    private final String value;

    @JsonCreator
    public static EvaluationStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(constant -> constant.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String serializer(){
        return value;
    }
}
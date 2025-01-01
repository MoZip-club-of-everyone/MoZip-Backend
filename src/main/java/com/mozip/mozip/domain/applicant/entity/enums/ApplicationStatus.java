package com.mozip.mozip.domain.applicant.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ApplicationStatus {
    UNCHECKED("미평가"), // 미평가
    HOLD("보류"), // 보류
    WAITLISTED("예비"), // 예비
    PASSED("합격"), // 합격
    FAILED("불합격"); // 불합격

    private final String value;

    @JsonCreator
    public static ApplicationStatus fromValue(String value) {
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
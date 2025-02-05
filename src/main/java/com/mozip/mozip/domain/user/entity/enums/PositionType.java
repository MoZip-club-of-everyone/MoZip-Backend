package com.mozip.mozip.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum PositionType {
    MASTER("마스터", true, true, true, true),
    MANAGER("관리", false, true, true, true),
    EVALUATE("평가", false, false, true, true),
    READ("조회", false, false, false, true);


    private final String value;
    private final boolean isMaster;
    private final boolean isManager;
    private final boolean isEvaluate;
    private final boolean isRead;

    @JsonCreator
    public static PositionType deserializer(String value) {
        for(PositionType positionType : PositionType.values()){
            if(positionType.getValue().equals(value)) {
                return positionType;
            }
        }
        return null;
    }

    @JsonValue
    public String serializer(){
        return value;
    }

    public static final List<PositionType> evaluablePositions = Arrays.stream(PositionType.values())
            .filter(PositionType::isEvaluate)
            .toList();
}
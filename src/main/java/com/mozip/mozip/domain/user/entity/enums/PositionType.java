package com.mozip.mozip.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PositionType {
    MASTER("마스터"),
    MANAGER("관리"),
    EVALUATE("평가"),
    READ("조회");

    private final String value;

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
}
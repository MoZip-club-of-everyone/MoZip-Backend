package com.mozip.mozip.domain.club.dto;

import com.mozip.mozip.domain.user.entity.enums.PositionType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PositionReqDto {
    private String userId;
    private PositionType positionName;
}
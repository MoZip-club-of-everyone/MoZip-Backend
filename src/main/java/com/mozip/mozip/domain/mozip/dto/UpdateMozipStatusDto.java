package com.mozip.mozip.domain.mozip.dto;

import com.mozip.mozip.domain.mozip.entity.enums.MozipStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMozipStatusDto {
    private MozipStatus status;
}

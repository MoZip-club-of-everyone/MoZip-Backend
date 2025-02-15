package com.mozip.mozip.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CertificationCodeReqDto {
    String phone;
    String code;
}

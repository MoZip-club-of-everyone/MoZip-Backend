package com.mozip.mozip.domain.club.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ClubCreateReqDto {
    private String name;
    private MultipartFile image;
}

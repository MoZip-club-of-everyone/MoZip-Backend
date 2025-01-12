package com.mozip.mozip.domain.club.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.club.entity.Club;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClubResponseDto {
    private String clubId;
    private String name;
    private String image;

    public static ClubResponseDto fromEntity(Club club){
        return ClubResponseDto.builder()
                .clubId(club.getId())
                .name(club.getName())
                .image(club.getImage())
                .build();
    }
}

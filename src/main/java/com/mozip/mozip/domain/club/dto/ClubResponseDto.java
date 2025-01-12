package com.mozip.mozip.domain.club.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.user.dto.PositionDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClubResponseDto {
    private String clubId;
    private String name;
    private String image;
    private List<PositionDto> positions;

    public ClubResponseDto(Club club){
        this.clubId = club.getId();
        this.name = club.getName();
        this.image = club.getImage();
        this.positions = club.getPosition().stream()
                .map(PositionDto::new)
                .collect(Collectors.toList());
    }

    public static List<ClubResponseDto> from(List<Club> clubs){
        return clubs.stream()
                .map(ClubResponseDto::new)
                .collect(Collectors.toList());
    }
}

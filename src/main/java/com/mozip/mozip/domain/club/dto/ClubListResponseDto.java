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
public class ClubListResponseDto {
    private String clubId;
    private String name;
    private String image;
    private List<PositionDto> positions;

    public ClubListResponseDto(Club club){
        this.clubId = club.getId();
        this.name = club.getName();
        this.image = club.getImage();
        this.positions = club.getPosition().stream()
                .map(PositionDto::new)
                .collect(Collectors.toList());
    }

    public static List<ClubListResponseDto> from(List<Club> clubs){
        return clubs.stream()
                .map(ClubListResponseDto::new)
                .collect(Collectors.toList());
    }
}

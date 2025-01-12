package com.mozip.mozip.domain.user.dto;

import com.mozip.mozip.domain.user.entity.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PositionDto {
    private String clubId;
    private String userId;
    private String positionName;

    public PositionDto(Position position){
        this.clubId = position.getClub().getId();
        this.userId = position.getUser().getId();
        this.positionName = position.getPositionName().name();
    }
}

package com.mozip.mozip.domain.mozip.dto;

import com.mozip.mozip.domain.mozip.entity.Mozip;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MozipResponseDto {
    private String id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isLoginRequired;
    private boolean isEditAvailable;
    private String descriptionBeforeMozip;
    private String descriptionAfterMozip;

    public static MozipResponseDto entityToDto(Mozip mozip) {
        return MozipResponseDto.builder()
                .id(mozip.getId())
                .title(mozip.getTitle())
                .description(mozip.getDescription())
                .startDate(mozip.getStartDate())
                .endDate(mozip.getEndDate())
                .isLoginRequired(mozip.isLoginRequired())
                .isEditAvailable(mozip.isEditAvailable())
                .descriptionBeforeMozip(mozip.getDescriptionBeforeMozip())
                .descriptionAfterMozip(mozip.getDescriptionAfterMozip())
                .build();
    }
}

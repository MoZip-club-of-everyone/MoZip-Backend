package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaperEvaluationData extends EvaluationData {
    private Integer paperScore;

    public static PaperEvaluationData from(Evaluation evaluation) {
        return PaperEvaluationData.builder()
                .evaluationId(evaluation.getId())
                .realname(evaluation.getEvaluator().getRealname())
                .paperScore(evaluation.getPaperScore())
                .build();
    }
} 
package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EvaluationData {
    private String evaluationId;
    private String realname;
    private Integer paperScore;
    private Integer interviewScore;

    public static EvaluationData from(Evaluation evaluation) {
        return EvaluationData.builder()
                .evaluationId(evaluation.getId())
                .realname(evaluation.getEvaluator().getRealname())
                .paperScore(evaluation.getPaperScore())
                .interviewScore(evaluation.getInterviewScore())
                .build();
    }
}

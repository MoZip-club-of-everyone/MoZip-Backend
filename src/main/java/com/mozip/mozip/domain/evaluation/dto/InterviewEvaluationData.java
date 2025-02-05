package com.mozip.mozip.domain.evaluation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InterviewEvaluationData extends EvaluationData {
    private Integer score;

    public static InterviewEvaluationData from(Evaluation evaluation) {
        return InterviewEvaluationData.builder()
                .evaluationId(evaluation.getId())
                .realname(evaluation.getEvaluator().getRealname())
                .score(evaluation.getInterviewScore())
                .build();
    }
} 
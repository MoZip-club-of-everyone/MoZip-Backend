package com.mozip.mozip.domain.applicant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.ToIntFunction;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant extends BaseTime {
    @Id
    @Column(name = "applicant_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mozip_id", nullable = false)
    private Mozip mozip;

    @JsonIgnore
    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations;

    @Setter
    private Long applicationNumber;

    @Setter
    private Double paperScoreAverage;

    @Setter
    private Double interviewScoreAverage;

    @Setter
    private Double paperScoreStandardDeviation;

    @Setter
    private Double interviewStandardDeviation;

    @Setter
    @Builder.Default
    private Boolean isRegistered = false;

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EvaluationStatus paperStatus = EvaluationStatus.UNEVALUATED;

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EvaluationStatus interviewStatus = EvaluationStatus.UNEVALUATED;

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EvaluationStatus totalStatus = EvaluationStatus.UNEVALUATED;

    // 서류 점수 평균 계산
    public Double calculatePaperAverage() {
        return calculateAverage(Evaluation::getPaperScore);
    }

    // 서류 점수 표준편차 계산
    public Double calculatePaperStandardDeviation() {
        return calculateStandardDeviation(Evaluation::getPaperScore);
    }

    // 면접 점수 평균 계산
    public Double calculateInterviewAverage() {
        return calculateAverage(Evaluation::getInterviewScore);
    }

    // 면접 점수 표준편차 계산
    public Double calculateInterviewStandardDeviation() {
        return calculateStandardDeviation(Evaluation::getInterviewScore);
    }

    // 평균 계산
    private Double calculateAverage(ToIntFunction<Evaluation> scoreFunction) {
        OptionalDouble average = evaluations.stream()
                .mapToInt(scoreFunction)
                .average();
        return average.isPresent() ? Math.round(average.getAsDouble() * 10.0) / 10.0 : null;
    }

    // 표준편차 계산
    private Double calculateStandardDeviation(ToIntFunction<Evaluation> scoreFunction) {
        Double average = calculateAverage(scoreFunction);
        if (average == null) return null;
        double variance = evaluations.stream()
                .mapToDouble(e -> Math.pow(scoreFunction.applyAsInt(e) - average, 2))
                .average()
                .orElse(0.0);
        return Math.round(Math.sqrt(variance) * 10.0) / 10.0;
    }
}
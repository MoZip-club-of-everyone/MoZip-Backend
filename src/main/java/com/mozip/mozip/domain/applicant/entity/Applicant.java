package com.mozip.mozip.domain.applicant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import com.mozip.mozip.domain.evaluation.entity.Evaluation;
import com.mozip.mozip.domain.interviewAnswer.entity.InterviewAnswer;
import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewAnswer> interviewAnswers;

    @JsonIgnore
    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaperAnswer> paperAnswers;

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
}
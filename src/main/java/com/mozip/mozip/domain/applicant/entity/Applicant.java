package com.mozip.mozip.domain.applicant.entity;

import com.mozip.mozip.domain.applicant.entity.enums.EvaluationStatus;
import com.mozip.mozip.domain.mozip.entity.Mozip;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

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

    private int applicationNumber;

    @Setter
    @Builder.Default
    private EvaluationStatus paperStatus = EvaluationStatus.UNEVALUATED;

    @Setter
    @Builder.Default
    private EvaluationStatus interviewStatus = EvaluationStatus.UNEVALUATED;

    @Setter
    @Builder.Default
    private EvaluationStatus totalStatus = EvaluationStatus.UNEVALUATED;
}
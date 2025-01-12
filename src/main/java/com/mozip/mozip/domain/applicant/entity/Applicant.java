package com.mozip.mozip.domain.applicant.entity;

import com.mozip.mozip.domain.applicant.entity.enums.ApplicationStatus;
import com.mozip.mozip.domain.club.entity.Mozip;
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

    @Builder.Default
    private ApplicationStatus paperStatus = ApplicationStatus.UNEVALUATED;

    @Builder.Default
    private ApplicationStatus interviewStatus = ApplicationStatus.UNEVALUATED;

    @Builder.Default
    private ApplicationStatus totalStatus = ApplicationStatus.UNEVALUATED;
}
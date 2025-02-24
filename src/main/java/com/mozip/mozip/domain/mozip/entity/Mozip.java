package com.mozip.mozip.domain.mozip.entity;

import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.mozip.entity.enums.MozipStatus;
import com.mozip.mozip.domain.paperQuestion.entity.PaperQuestion;
import com.mozip.mozip.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mozip extends BaseTime {
    @Id
    @Column(name = "mozip_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Setter
    @Column(nullable = false)
    private LocalDateTime endDate;


    @Setter
    @Column(nullable = false)
    private String description;
    @Setter
    @Column(nullable = false)
    private String descriptionBeforeMozip;
    @Setter
    @Column(nullable = false)
    private String descriptionAfterMozip;

    private boolean isLoginRequired;
    private boolean isEditAvailable;

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MozipStatus status = MozipStatus.BEFORE_MOZIP;

    @Setter
    @OneToMany(mappedBy = "mozip", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaperQuestion> paperQuestions;
}
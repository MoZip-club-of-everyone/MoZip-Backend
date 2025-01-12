package com.mozip.mozip.domain.club.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mozip.mozip.domain.question.entity.PaperQuestion;
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

    @JsonIgnore
    @OneToMany(mappedBy = "mozip", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaperQuestion> paperQuestions;
}
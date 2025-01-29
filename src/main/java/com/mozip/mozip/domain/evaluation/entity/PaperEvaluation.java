package com.mozip.mozip.domain.evaluation.entity;

import com.mozip.mozip.domain.paperAnswer.entity.PaperAnswer;
import com.mozip.mozip.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaperEvaluation extends BaseTime {
    @Id
    @Column(name = "paper_evaluation_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paper_answer_id", nullable = false)
    private PaperAnswer paperAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    private int score;
}
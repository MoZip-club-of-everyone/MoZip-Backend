package com.mozip.mozip.domain.evaluation.entity;

import com.mozip.mozip.domain.answer.entity.InterviewAnswer;
import com.mozip.mozip.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewComment extends BaseTime {
    @Id
    @Column(name = "interview_comment_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_answer_id", nullable = false)
    private InterviewAnswer interviewAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;
}
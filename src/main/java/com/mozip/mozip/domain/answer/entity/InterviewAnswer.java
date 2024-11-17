package com.mozip.mozip.domain.answer.entity;

import com.mozip.mozip.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewAnswer extends BaseTime {
    @Id
    @Column(name = "interview_answer_id")
    @Builder.Default
    private final String id = new ULID().nextULID();
}

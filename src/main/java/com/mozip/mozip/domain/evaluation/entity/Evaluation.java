package com.mozip.mozip.domain.evaluation.entity;

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
public class Evaluation extends BaseTime {
    @Id
    @Column(name = "evaluation_id")
    @Builder.Default
    private final String id = new ULID().nextULID();
}

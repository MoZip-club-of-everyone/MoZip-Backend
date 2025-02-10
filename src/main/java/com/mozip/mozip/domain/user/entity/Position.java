package com.mozip.mozip.domain.user.entity;

import com.mozip.mozip.domain.club.entity.Club;
import com.mozip.mozip.domain.user.entity.enums.PositionType;
import com.mozip.mozip.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position extends BaseTime {
    @Id
    @Column(name = "position_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @Setter
    @Enumerated(EnumType.STRING)
    private PositionType positionName;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
}

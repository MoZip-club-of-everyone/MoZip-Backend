package com.mozip.mozip.domain.user.entity;

import com.mozip.mozip.domain.user.entity.enums.Role;
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
public class User extends BaseTime {
    @Id
    @Column(name = "user_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @Column(unique = true)
    private String email;

    @Setter
    private String password;

    private String realname;

    private String image;

    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isJoin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> position;

    public void updateInfo(String realname, String phone) {
        this.realname = realname;
        this.phone = phone;
    }
}

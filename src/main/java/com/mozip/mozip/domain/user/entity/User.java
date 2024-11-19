package com.mozip.mozip.domain.user.entity;

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

    private String username;

    private String password;

    private String realname;

    private String image;

    private String email;

    private String phone;

    public enum RoleType {
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_USER("ROLE_USER");
        private final String roleName;
        RoleType(String roleName) {
            this.roleName = roleName;
        }
        public String getRoleName() {
            return roleName;
        }
    }

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private boolean isJoin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> position;
}

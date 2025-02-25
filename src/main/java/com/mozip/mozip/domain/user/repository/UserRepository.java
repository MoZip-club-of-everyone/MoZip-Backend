package com.mozip.mozip.domain.user.repository;

import com.mozip.mozip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRealnameAndPhone(String name, String phone);

    Optional<User> findByPhone(String phone);

    Optional<User> findByRealname(String realname);
}

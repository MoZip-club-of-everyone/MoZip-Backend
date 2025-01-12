package com.mozip.mozip.domain.user.service;

import com.mozip.mozip.domain.user.dto.SignupRequest;
import com.mozip.mozip.domain.user.entity.enums.Role;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.user.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입 처리
    public void joinProcess(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new DuplicateRequestException("이미 존재하는 이메일입니다.");
        }
        User newUser = User.builder()
                .email(signupRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(signupRequest.getPassword()))
                .role(Role.ROLE_USER)
                .realname(signupRequest.getRealname())
                .phone(signupRequest.getPhone())
                .isJoin(true)
                .build();
        userRepository.save(newUser);
    }
}

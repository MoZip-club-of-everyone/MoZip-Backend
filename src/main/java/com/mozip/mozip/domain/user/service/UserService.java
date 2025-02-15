package com.mozip.mozip.domain.user.service;

import com.mozip.mozip.domain.applicant.dto.ApplicantInfoRequest;
import com.mozip.mozip.domain.user.dto.SignupRequest;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.user.entity.enums.Role;
import com.mozip.mozip.domain.user.repository.UserRepository;
import com.mozip.mozip.global.dto.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User가 없습니다"));
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User가 없습니다."));
    }

    public User getUserByRealname(String realname) {
        return userRepository.findByRealname(realname)
                .orElseThrow(() -> new EntityNotFoundException(("User가 없습니다.")));
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("User가 없습니다."));
    }

    public User getUserByRealnameAndPhone(String name, String phone){
        return userRepository.findByRealnameAndPhone(name, phone)
                .orElseThrow(()-> new EntityNotFoundException(("User가 없습니다.")));
    }

    // 회원가입 처리
    public void joinProcess(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
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

    public User createGuestUser() {
        User newUser = User.builder()
                .role(Role.ROLE_GUEST)
                .isJoin(false)
                .build();
        return userRepository.save(newUser);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return createGuestUser();
        }
        return ((CustomUserDetails) authentication.getPrincipal()).user();
    }

    public void updateApplicantUserInfo(User user, ApplicantInfoRequest request) {
        user.updateInfo(request.getRealname(), request.getPhone());
        userRepository.save(user);
    }

    public void updateUserPassword(String userId, String password) {
        User user = getUserById(userId);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }
}

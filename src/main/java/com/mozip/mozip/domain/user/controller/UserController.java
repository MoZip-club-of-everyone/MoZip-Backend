package com.mozip.mozip.domain.user.controller;

import com.mozip.mozip.domain.user.dto.SignupRequest;
import com.mozip.mozip.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        log.info("signup email: {}", signupRequest.getUsername());
        userService.joinProcess(signupRequest);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}

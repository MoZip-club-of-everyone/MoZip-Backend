package com.mozip.mozip.domain.user.controller;

import com.mozip.mozip.domain.user.dto.SignupRequest;
import com.mozip.mozip.domain.user.service.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        try{
            userService.joinProcess(signupRequest);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch(DuplicateRequestException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("이미 존재하는 아이디입니다.");
        }
    }
}

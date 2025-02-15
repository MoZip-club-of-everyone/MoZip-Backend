package com.mozip.mozip.domain.user.controller;

import com.mozip.mozip.domain.user.dto.*;
import com.mozip.mozip.domain.user.entity.User;
import com.mozip.mozip.domain.user.exception.DuplicateRealNameException;
import com.mozip.mozip.domain.user.service.SmsService;
import com.mozip.mozip.domain.user.service.UserService;
import com.mozip.mozip.global.dto.CustomUserDetails;
import com.mozip.mozip.global.jwt.JwtUtil;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final SmsService smsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/join")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        try{
            userService.joinProcess(signupRequest);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch(DuplicateRequestException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("이미 존재하는 아이디입니다.");
        } catch (DuplicateRealNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/certification-number")
    public ResponseEntity<String> sendVerificationCode(@RequestBody CertificationInfoReqDto certificationInfoReqDto){
        try{
            userService.getUserByRealnameAndPhone(certificationInfoReqDto.getName(), certificationInfoReqDto.getPhone());
            if (smsService.sendVerificationCode(certificationInfoReqDto.getPhone())){
                return ResponseEntity.ok("인증번호가 발송됐습니다.");
            }
            return ResponseEntity
                    .status((HttpStatus.INTERNAL_SERVER_ERROR))
                    .body("인증코드 전송을 실패했습니다. 다시 시도해주세요.");
        } catch(EntityNotFoundException e){
            return ResponseEntity
                    .status((HttpStatus.BAD_REQUEST))
                    .body("이름 또는 전화번호를 확인해주세요.");
        }
    }

    @PutMapping("/password")
    public ResponseEntity<String> setPassword(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody PasswordReqDto passwordReqDto){
        if (customUserDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원이 아닙니다.");
        }
        if (!customUserDetails.getId().equals(passwordReqDto.getUserId())) {
            System.out.println(customUserDetails.getId());
            System.out.println(passwordReqDto.getUserId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("다른 회원입니다.");
        }
        userService.updateUserPassword(passwordReqDto.getUserId(), passwordReqDto.getPassword());
        return ResponseEntity.ok("비밀번호 변경이 완료되었습니다.");
    }

    @DeleteMapping("/certification-number/id")
    public ResponseEntity<?> verifyCodeForId(@RequestBody CertificationCodeReqDto certificationCodeReqDto) {
        if (smsService.verifyCode(certificationCodeReqDto.getPhone(), certificationCodeReqDto.getCode())){
            String email = userService.getUserByPhone(certificationCodeReqDto.getPhone()).getEmail();
            return ResponseEntity.ok(new EmailResDto(email));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("인증번호가 일치하지 않습니다.");
        }
    }

    @DeleteMapping("/certification-number/password")
    public ResponseEntity<?> verifyCodeForPw(@RequestBody CertificationCodeReqDto certificationCodeReqDto) {
        if (smsService.verifyCode(certificationCodeReqDto.getPhone(), certificationCodeReqDto.getCode())){
            User user = userService.getUserByPhone(certificationCodeReqDto.getPhone());
            String userId = user.getId();
            String accessToken = jwtUtil.createJwt(user.getEmail(), user.getRole().getRoleName());
            return ResponseEntity.ok(new IdResDto(userId, accessToken));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("인증번호가 일치하지 않습니다.");
        }
    }
}

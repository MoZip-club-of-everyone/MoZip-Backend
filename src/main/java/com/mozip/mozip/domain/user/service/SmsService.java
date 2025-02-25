package com.mozip.mozip.domain.user.service;

import com.mozip.mozip.domain.user.entity.VerificationCode;
import com.mozip.mozip.domain.user.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final VerificationCodeRepository verificationCodeRepository;

    @Value("${sms.api-key}")
    private String apiKey;
    @Value("${sms.api-secret}")
    private String secretKey;

    public boolean sendVerificationCode(String phone) {
        deleteBeforeCode(phone);
        String verificationCode = String.valueOf((int) (Math.random() * 900000) + 100000);
        String message = "인증번호는 [" + verificationCode + "] 입니다.";
        boolean isSent = sendSms(phone, message);

        if (isSent) {
            VerificationCode entity = new VerificationCode();
            entity.setPhone(phone);
            entity.setCode(verificationCode);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setExpiresAt(LocalDateTime.now().plusMinutes(5)); // 5분

            verificationCodeRepository.save(entity);
        } else {
            System.out.println("인증코드 생성에 실패하였습니다.");
        }

        return isSent;
    }

    public boolean verifyCode(String phone, String code) {
        if (verificationCodeRepository.findByPhoneAndCode(phone, code)
                .filter(v -> v.getExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent()){
            verificationCodeRepository.deleteByPhoneAndCode(phone, code);
            return true;
        } else {
            return false;
        }
    }

    private boolean sendSms(String phone, String message) {
        Message coolsms = new Message(apiKey, secretKey);
        HashMap<String, String> params = new HashMap<>();
        params.put("to", phone);
        params.put("from", "01040694033");
        params.put("type", "SMS");
        params.put("text", message);

        try {
            JSONObject response = coolsms.send(params);
            System.out.println("SMS를 성공적으로 전송하였습니다: " + response.toJSONString());
            return true;
        } catch (CoolsmsException e) {
            System.out.println("SMS 전송에 실패하였습니다: " + e.getCode() + " - " + e.getMessage());
            return false;
        }
    }

    private void deleteBeforeCode(String phone){
        verificationCodeRepository.deleteByPhone(phone);
    }
}

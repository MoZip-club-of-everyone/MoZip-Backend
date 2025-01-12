package com.mozip.mozip.domain.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User를 찾을 수 없습니다.");
    }

    public UserNotFoundException(String userId) {
        super("Id: " + userId + " 의 User를 찾을 수 없습니다.");
    }
}

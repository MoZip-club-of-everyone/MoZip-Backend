package com.mozip.mozip.domain.club.exception;

public class MozipNotFoundException extends RuntimeException{

    public MozipNotFoundException() {
        super("Mozip을 찾을 수 없습니다.");
    }

    public MozipNotFoundException(String mozipId) {
        super("Id: " + mozipId + " 의 Mozip을 찾을 수 없습니다.");
    }
}
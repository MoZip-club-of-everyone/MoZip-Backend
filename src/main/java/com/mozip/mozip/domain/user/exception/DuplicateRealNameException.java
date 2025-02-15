package com.mozip.mozip.domain.user.exception;

public class DuplicateRealNameException extends RuntimeException{
    public DuplicateRealNameException() { super("같은 이름의 사용자가 있습니다.");}
}

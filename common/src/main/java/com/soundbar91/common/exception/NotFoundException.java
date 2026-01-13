package com.soundbar91.common.exception;

/**
 * 리소스를 찾을 수 없는 경우 발생하는 예외
 */
public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }

    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}

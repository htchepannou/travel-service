package io.tchepannou.k.travel.exception;

public class InvalidRequestException extends BusinessException{
    public InvalidRequestException(final BusinessErrors errorCode) {
        super(errorCode);
    }
    public InvalidRequestException(final BusinessErrors errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}

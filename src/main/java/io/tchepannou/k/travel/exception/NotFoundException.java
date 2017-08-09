package io.tchepannou.k.travel.exception;

public class NotFoundException extends BusinessException{
    public NotFoundException(final BusinessErrors errorCode) {
        super(errorCode);
    }
}

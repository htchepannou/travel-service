package io.tchepannou.k.travel.exception;

public class BusinessException extends RuntimeException {
    private BusinessErrors errorCode;

    public BusinessException(final BusinessErrors errorCode) {
        this.errorCode = errorCode;
    }
    public BusinessException(final BusinessErrors errorCode, final Throwable cause) {
        super(cause);

        this.errorCode = errorCode;
    }

    public BusinessErrors getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorCode.getCode() + " - " + errorCode.getDescription();
    }
}

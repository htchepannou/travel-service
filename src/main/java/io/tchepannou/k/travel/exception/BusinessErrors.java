package io.tchepannou.k.travel.exception;

public enum BusinessErrors {
    BAD_REQUEST("BAD_REQUEST", "Request is invalid"),
    ASSET_NOT_FOUND("ASSET_NOT_FOUND", "Asset not found"),
    INVALID_ASSET_TYPE("INVALID_ASSET_TYPE", "Invalid asset type"),
    INVALID_PRICE_TYPE("INVALID_PRICE_TYPE", "Invalid price type"),
    INVALID_PRODUCT_TYPE("INVALID_PRODUCT_TYPE", "Invalid product type"),
    INVALID_DEPARTURE_TIME_FORMAT("INVALID_DEPARTURE_TIME_FORMAT", "departureTime format is invalid. Expecting HH:mm"),
    INVALID_ARRIVAL_TIME_FORMAT("INVALID_ARRIVAL_TIME_FORMAT", "arrivalTime format is invalid. Expecting HH:mm"),
    INVALID_FROM_DATE_FORMAT("INVALID_FROM_DATE_FORMAT", "fromDate format is invalid. Expecting yyyy-MM-dd"),
    PRICE_NOT_FOUND("PRICE_NOT_FOUND", "Product not found"),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "Product not found"),
    SCHEDULE_NOT_FOUND("SCHEDULE_NOT_FOUND", "Schedule not found"),
    SCHEDULED_TIME_NOT_FOUND("SCHEDULED_TIME_NOT_FOUND", "Scheduled time not found")
    ;

    private String code;
    private String description;

    BusinessErrors(String code, String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

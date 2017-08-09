package io.tchepannou.k.travel.controller;

import io.tchepannou.k.travel.client.response.ErrorDTO;
import io.tchepannou.k.travel.client.response.ErrorResponse;
import io.tchepannou.k.travel.exception.BusinessException;
import io.tchepannou.k.travel.exception.BusinessErrors;
import io.tchepannou.k.travel.exception.InvalidRequestException;
import io.tchepannou.k.travel.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final ErrorResponse response = new ErrorResponse();
        final List<ObjectError> validationErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError validationError : validationErrors){
            ErrorDTO error;
            if (validationError instanceof FieldError){
                final FieldError fieldError = (FieldError)validationError;
                error = createError(
                        BusinessErrors.BAD_REQUEST.getCode(),
                        String.format("%s=%s - %s",
                                fieldError.getField(),
                                fieldError.getRejectedValue(),
                                fieldError.getDefaultMessage())
                );
            } else {
                error = createError(BusinessErrors.BAD_REQUEST.getCode(), validationError.getDefaultMessage());
            }

            response.getErrors().add(error);
        }

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex) {
        final ErrorResponse response = createErrorResponse(ex);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final InvalidRequestException ex) {
        final ErrorResponse response = createErrorResponse(ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException ex) {
        final ErrorResponse response = createErrorResponse(ex);
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }


    private ErrorResponse createErrorResponse(final BusinessException ex){
        final ErrorResponse response = new ErrorResponse();
        response.getErrors().add(createError(ex.getErrorCode()));
        return response;
    }

    private ErrorDTO createError(BusinessErrors code){
        return createError(code.getCode(), code.getDescription());
    }

    private ErrorDTO createError(String code, String description){
        final ErrorDTO error = new ErrorDTO();
        error.setCode(code);
        error.setDescription(description);
        return error;
    }

}

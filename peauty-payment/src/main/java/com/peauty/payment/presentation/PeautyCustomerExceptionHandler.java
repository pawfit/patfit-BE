package com.peauty.payment.presentation;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyApiResponse;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class PeautyCustomerExceptionHandler {

    private static final String Peauty_API_EXCEPTION_LOG = "[PeautyCustomerException] : {}";

    private ResponseEntity<PeautyApiResponse<Void>> toResponseEntity(PeautyException ex) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(PeautyApiResponse.error(
                        ex.getResponseCode(),
                        ex.getErrorMessage(),
                        ex.getServiceMessage()
                ));
    }

    @ExceptionHandler
    public ResponseEntity<PeautyApiResponse<Void>> handlePeautyCustomerException(PeautyException ex) {
        log.error(Peauty_API_EXCEPTION_LOG, ex.getMessage(), ex);
        return toResponseEntity(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PeautyApiResponse<Void>> handleUncheckedException(Exception ex) {
        log.error("[Unknown Error] : {}", ex.getMessage(), ex);
        return toResponseEntity(new PeautyException(PeautyResponseCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({
            ServletRequestBindingException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            BindException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<PeautyApiResponse<Void>> exceptionHandler(Exception ex) {
        String message;

        if (ex instanceof MethodArgumentNotValidException) {
            message = getSortedErrors(((MethodArgumentNotValidException) ex).getBindingResult()).toString();
        } else if (ex instanceof BindException) {
            message = getSortedErrors(((BindException) ex).getBindingResult()).toString();
        } else if (ex instanceof HttpMessageNotReadableException) {
            String exMessage = ex.getMessage();
            message = exMessage != null && exMessage.contains(":")
                    ? exMessage.split(":")[0]
                    : exMessage;
        } else {
            message = PeautyResponseCode.BAD_REQUEST.getMessage();
        }

        log.warn("[BAD_REQUEST] : {}", message, ex);
        return toResponseEntity(new PeautyException(
                PeautyResponseCode.BAD_REQUEST,
                message != null ? message : PeautyResponseCode.BAD_REQUEST.getMessage(),
                PeautyResponseCode.BAD_REQUEST.getServiceMessage()
        ));
    }

    private List<String> getSortedErrors(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : bindingResult.getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        return errors;
    }
}

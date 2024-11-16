package com.peauty.domain.exception;


import com.peauty.domain.response.PeautyResponseCode;

public class PeautyException extends RuntimeException {
    private final PeautyResponseCode peautyResponseCode;
    private final String errorMessage;
    private final String serviceMessage;

    public PeautyException(PeautyResponseCode peautyResponseCode) {
        this(peautyResponseCode, peautyResponseCode.getMessage(), peautyResponseCode.getServiceMessage());
    }

    public PeautyException(PeautyResponseCode peautyResponseCode, String errorMessage, String serviceMessage) {
        super(errorMessage);
        this.peautyResponseCode = peautyResponseCode;
        this.errorMessage = errorMessage;
        this.serviceMessage = serviceMessage;
    }

    public PeautyResponseCode getResponseCode() {
        return peautyResponseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getServiceMessage() {
        return serviceMessage;
    }
}

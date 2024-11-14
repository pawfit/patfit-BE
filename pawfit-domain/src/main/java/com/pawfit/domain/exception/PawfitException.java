package com.pawfit.domain.exception;


import com.pawfit.domain.response.PawfitResponseCode;

public class PawfitException extends RuntimeException {
    private final PawfitResponseCode pawfitResponseCode;
    private final String errorMessage;
    private final String serviceMessage;

    public PawfitException(PawfitResponseCode pawfitResponseCode) {
        this(pawfitResponseCode, pawfitResponseCode.getMessage(), pawfitResponseCode.getServiceMessage());
    }

    public PawfitException(PawfitResponseCode pawfitResponseCode, String errorMessage, String serviceMessage) {
        super(errorMessage);
        this.pawfitResponseCode = pawfitResponseCode;
        this.errorMessage = errorMessage;
        this.serviceMessage = serviceMessage;
    }

    public PawfitResponseCode getResponseCode() {
        return pawfitResponseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getServiceMessage() {
        return serviceMessage;
    }
}

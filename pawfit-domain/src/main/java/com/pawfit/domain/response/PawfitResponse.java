package com.pawfit.domain.response;

import java.util.Objects;

public class PawfitResponse<T> {
    private final String responseCode;
    private final String errorMessage;
    private final String serviceErrorMessage;
    private final T data;

    private PawfitResponse(String responseCode, String errorMessage, String serviceErrorMessage, T data) {
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.serviceErrorMessage = serviceErrorMessage;
        this.data = data;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getServiceErrorMessage() {
        return serviceErrorMessage;
    }

    public T getData() {
        return data;
    }

    public static <T> PawfitResponse<T> ok() {
        return ok(null);
    }

    public static <T> PawfitResponse<T> ok(T data) {
        return new PawfitResponse<>(
                PawfitResponseCode.OK.getCode(),
                PawfitResponseCode.OK.getMessage(),
                PawfitResponseCode.OK.getServiceMessage(),
                data
        );
    }

    public static <T> PawfitResponse<T> ok(PawfitResponseCode pawfitResponseCode) {
        return ok(pawfitResponseCode, null);
    }

    public static <T> PawfitResponse<T> ok(PawfitResponseCode pawfitResponseCode, T data) {
        return new PawfitResponse<>(
                pawfitResponseCode.getCode(),
                pawfitResponseCode.getMessage(),
                pawfitResponseCode.getServiceMessage(),
                data
        );
    }

    public static <T> PawfitResponse<T> error(PawfitResponseCode pawfitResponseCode) {
        return new PawfitResponse<>(
                pawfitResponseCode.getCode(),
                pawfitResponseCode.getMessage(),
                pawfitResponseCode.getServiceMessage(),
                null
        );
    }

    public static <T> PawfitResponse<T> error(
            PawfitResponseCode pawfitResponseCode,
            String errorMessage,
            String serviceErrorMessage
    ) {
        return new PawfitResponse<>(
                pawfitResponseCode.getCode(),
                errorMessage,
                serviceErrorMessage,
                null
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PawfitResponse<?> that = (PawfitResponse<?>) o;
        return Objects.equals(responseCode, that.responseCode) &&
                Objects.equals(errorMessage, that.errorMessage) &&
                Objects.equals(serviceErrorMessage, that.serviceErrorMessage) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, errorMessage, serviceErrorMessage, data);
    }

    @Override
    public String toString() {
        return "PawfitResponse{" +
                "responseCode='" + responseCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", serviceErrorMessage='" + serviceErrorMessage + '\'' +
                ", data=" + data +
                '}';
    }
}

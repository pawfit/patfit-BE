package com.peauty.domain.response;

import java.util.Objects;

public class PeautyApiResponse<T> {
    private final String responseCode;
    private final String errorMessage;
    private final String serviceErrorMessage;
    private final T data;

    private PeautyApiResponse(String responseCode, String errorMessage, String serviceErrorMessage, T data) {
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

    public static <T> PeautyApiResponse<T> ok() {
        return ok(null);
    }

    public static <T> PeautyApiResponse<T> ok(T data) {
        return new PeautyApiResponse<>(
                PeautyResponseCode.OK.getCode(),
                PeautyResponseCode.OK.getMessage(),
                PeautyResponseCode.OK.getServiceMessage(),
                data
        );
    }

    public static <T> PeautyApiResponse<T> ok(PeautyResponseCode peautyResponseCode) {
        return ok(peautyResponseCode, null);
    }

    public static <T> PeautyApiResponse<T> ok(PeautyResponseCode peautyResponseCode, T data) {
        return new PeautyApiResponse<>(
                peautyResponseCode.getCode(),
                peautyResponseCode.getMessage(),
                peautyResponseCode.getServiceMessage(),
                data
        );
    }

    public static <T> PeautyApiResponse<T> error(PeautyResponseCode peautyResponseCode) {
        return new PeautyApiResponse<>(
                peautyResponseCode.getCode(),
                peautyResponseCode.getMessage(),
                peautyResponseCode.getServiceMessage(),
                null
        );
    }

    public static <T> PeautyApiResponse<T> error(
            PeautyResponseCode peautyResponseCode,
            String errorMessage,
            String serviceErrorMessage
    ) {
        return new PeautyApiResponse<>(
                peautyResponseCode.getCode(),
                errorMessage,
                serviceErrorMessage,
                null
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeautyApiResponse<?> that = (PeautyApiResponse<?>) o;
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
        return "PeautyResponse{" +
                "responseCode='" + responseCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", serviceErrorMessage='" + serviceErrorMessage + '\'' +
                ", data=" + data +
                '}';
    }
}

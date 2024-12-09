package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CompletePaymentCallbackResult;

import java.time.LocalDateTime;

public record CompletePaymentCallbackResponse(
        String shopName,
        Integer price,
        LocalDateTime paymentDated,
        String paymentStatus
) {
    public static CompletePaymentCallbackResponse from(CompletePaymentCallbackResult result) {
        return null;
    }
}

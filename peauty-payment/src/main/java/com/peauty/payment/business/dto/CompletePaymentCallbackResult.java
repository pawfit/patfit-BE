package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Payment;

import java.time.LocalDateTime;

public record CompletePaymentCallbackResult(
        String workspaceName,
        String threadId,
        LocalDateTime paymentDated,
        Integer price
) {
    // 전체 값 보내주기
    public static CompletePaymentCallbackResult from(Payment savedPayment) {
        return null;
    }
}

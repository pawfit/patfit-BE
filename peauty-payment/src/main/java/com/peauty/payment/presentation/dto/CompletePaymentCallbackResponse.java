package com.peauty.payment.presentation.dto;

import com.peauty.domain.payment.PaymentStatus;
import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompletePaymentCallbackResponse(
        String shopName,
        Long orderId,
        Long depositPrice,
        Long actualPrice,
        LocalDateTime paymentDate,
        PaymentStatus paymentStatus
) {
    public static CompletePaymentCallbackResponse from(CompletePaymentCallbackResult result) {
        return CompletePaymentCallbackResponse.builder()
                .orderId(result.orderId())
                .shopName(result.workspaceName())
                .depositPrice(result.depositPrice())
                .paymentStatus(result.paymentStatus())
                .paymentDate(result.paymentEventTimestamp())
                .build();
    }
}

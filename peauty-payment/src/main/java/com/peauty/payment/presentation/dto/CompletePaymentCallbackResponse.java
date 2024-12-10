package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompletePaymentCallbackResponse(
        String shopName,
        Long price,
        LocalDateTime paymentDated,
        String paymentStatus
) {
    public static CompletePaymentCallbackResponse from(CompletePaymentCallbackResult result) {
        return CompletePaymentCallbackResponse.builder()
                .shopName(result.workspaceName())
                .price(result.price())
                .paymentStatus(null)
                .paymentDated(result.paymentDated())
                .build();
    }
}

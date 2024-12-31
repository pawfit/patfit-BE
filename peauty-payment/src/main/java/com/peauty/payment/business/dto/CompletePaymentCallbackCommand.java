package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Order;
import lombok.Builder;

@Builder
public record CompletePaymentCallbackCommand(
        Long orderId,
        Long depositPrice,
        String paymentUuid
) {
    public CompletePaymentCallbackResult from(Order order) {
        return CompletePaymentCallbackResult.builder()
                .build();
    }
}

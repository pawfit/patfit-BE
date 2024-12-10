package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CompletePaymentCallbackCommand;
import lombok.Builder;

@Builder
public record CompletePaymentCallbackRequest(
        Long orderId,
        Long depositPrice,
        String paymentUuid
) {
    public CompletePaymentCallbackCommand toCommand() {
        return CompletePaymentCallbackCommand.builder()
                .depositPrice(depositPrice)
                .paymentUuid(paymentUuid)
                .orderId(orderId)
                .build();
    }
}

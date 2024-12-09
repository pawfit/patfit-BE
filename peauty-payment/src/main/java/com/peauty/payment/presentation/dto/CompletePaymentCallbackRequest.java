package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CompletePaymentCallbackCommand;
import lombok.Builder;

@Builder
public record CompletePaymentCallbackRequest(
        Integer price,
        String paymentUuid
) {
    public CompletePaymentCallbackCommand toCommand() {
        return new CompletePaymentCallbackCommand(
                price,
                paymentUuid
        );
    }
}

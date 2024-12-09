package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CompletePaymentCommand;

public record CompletePaymentRequest(
        String uuid
) {
    public CompletePaymentCommand toCommand() {
        return new CompletePaymentCommand(
            uuid
        );
    }
}

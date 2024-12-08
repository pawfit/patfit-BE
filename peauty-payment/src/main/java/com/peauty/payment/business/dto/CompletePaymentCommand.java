package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Order;

public record CompletePaymentCommand(
        String uuid
) {
    public Order toDomain() {
        return null;
    }
}

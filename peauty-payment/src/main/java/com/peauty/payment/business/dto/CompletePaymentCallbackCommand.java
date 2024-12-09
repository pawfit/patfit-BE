package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Order;
import lombok.Builder;

@Builder
public record CompletePaymentCallbackCommand(
        Integer price,
        String uuid
) {
    public Order toDomain() {
        return null;
    }
}

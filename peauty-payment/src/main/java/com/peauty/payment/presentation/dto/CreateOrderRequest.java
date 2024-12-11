package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CreateOrderCommand;

public record CreateOrderRequest(
        Long depositPrice,
        String orderUuid
) {
    public CreateOrderCommand toCommand() {
        return CreateOrderCommand.builder()
                .depositPrice(depositPrice)
                .build();
    }
}

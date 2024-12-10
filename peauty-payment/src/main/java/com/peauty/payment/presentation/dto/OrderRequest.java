package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.OrderCommand;

public record OrderRequest(
        Long depositPrice,
        String orderUuid
) {
    public OrderCommand toCommand() {
        return OrderCommand.builder()
                .depositPrice(depositPrice)
                .build();
    }
}

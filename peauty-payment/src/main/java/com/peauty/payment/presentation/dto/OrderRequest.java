package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.OrderCommand;

public record OrderRequest(
        Long cost
) {
    public OrderCommand toCommand() {
        return OrderCommand.builder()
                .cost(cost)
                .build();
    }
}

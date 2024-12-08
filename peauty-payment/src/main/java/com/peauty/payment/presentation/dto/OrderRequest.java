package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.OrderCommand;

public record OrderRequest(
        Integer cost
) {
    public OrderCommand toCommand() {
        return OrderCommand.builder()
                .cost(cost)
                .build();
    }
}

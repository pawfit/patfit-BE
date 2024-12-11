package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CreateOrderCommand;

public record CreateOrderRequest(
        Long price,
        Long depositPrice
        // TODO. UUID를 실제로 포트원에서 받은 정보 중 하나로 설정해야할 지 고민
        // String orderUuid
) {
    public CreateOrderCommand toCommand() {
        return CreateOrderCommand.builder()
                .price(price)
                .depositPrice(depositPrice)
                .build();
    }
}

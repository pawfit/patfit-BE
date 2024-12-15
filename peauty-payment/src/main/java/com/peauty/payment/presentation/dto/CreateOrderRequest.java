package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CreateOrderCommand;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateOrderRequest(
        @Schema(description = "견적서에 적힌 금액", example = "20000")
        Long price,
        @Schema(description = "예약금(견적서에서 50%만 결제)", example = "10000")
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

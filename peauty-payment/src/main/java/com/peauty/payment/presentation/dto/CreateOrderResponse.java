package com.peauty.payment.presentation.dto;

import com.peauty.domain.payment.PaymentStatus;
import com.peauty.payment.business.dto.CreateOrderResult;
import lombok.Builder;

@Builder
public record CreateOrderResponse(
        Long orderId,
        Long depositPrice,
        String uuid,
        PaymentStatus paymentStatus
) {
    public static CreateOrderResponse from(CreateOrderResult createOrderResult) {
        return CreateOrderResponse.builder()
                .orderId(createOrderResult.orderId())
                .depositPrice(createOrderResult.depositPrice())
                .uuid(createOrderResult.uuid())
                .paymentStatus(createOrderResult.paymentStatus())
                .build();
    }
}

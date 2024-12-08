package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.OrderResult;
import lombok.Builder;

@Builder
public record OrderResponse(
        Long orderId,
        Integer cost,
        String uuid,
        Boolean paymentStatus
) {
    public static OrderResponse from(OrderResult orderResult) {
        return OrderResponse.builder()
                .orderId(orderResult.orderId())
                .cost(orderResult.cost())
                .uuid(orderResult.uuid())
                .paymentStatus(orderResult.paymentStatus())
                .build();
    }
}

package com.peauty.payment.presentation.dto;

import com.peauty.domain.payment.PaymentStatus;
import com.peauty.payment.business.dto.OrderResult;
import lombok.Builder;

@Builder
public record OrderResponse(
        Long orderId,
        Integer price,
        String uuid,
        PaymentStatus paymentStatus
) {
    public static OrderResponse from(OrderResult orderResult) {
        return OrderResponse.builder()
                .orderId(orderResult.orderId())
                .price(orderResult.price())
                .uuid(orderResult.uuid())
                .paymentStatus(orderResult.paymentStatus())
                .build();
    }
}

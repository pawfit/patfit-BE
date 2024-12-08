package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Order;
import lombok.Builder;

@Builder
public record OrderResult(
        Long orderId,
        String uuid,
        Integer cost,
        Boolean paymentStatus
) {

    public static OrderResult from(Order order) {
        return OrderResult.builder()
                .orderId(order.getOrderId())
                .cost(order.getCost())
                .paymentStatus(order.getIsPaymentCompleted())
                .build();
    }
}

package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.PaymentStatus;
import lombok.Builder;

@Builder
public record OrderResult(
        Long orderId,
        String uuid,
        Integer price,
        PaymentStatus paymentStatus
) {

    public static OrderResult from(Order order) {
        return OrderResult.builder()
                .orderId(order.getOrderId())
                .price(order.getPrice())
                .paymentStatus(order.getPayment().getStatus())
                .build();
    }
}

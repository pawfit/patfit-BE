package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.PaymentStatus;
import lombok.Builder;

@Builder
public record CreateOrderResult(
        Long orderId,
        String uuid,
        Long depositPrice,
        PaymentStatus paymentStatus
) {

    public static CreateOrderResult from(Order order) {
        return CreateOrderResult.builder()
                .orderId(order.getOrderId())
                .depositPrice(order.getDepositPrice())
                .paymentStatus(order.getPayment().getStatus())
                .build();
    }
}

package com.peauty.payment.business.dto;

import com.peauty.domain.payment.*;
import lombok.Builder;


@Builder
public record CreateOrderCommand(
        Long price,
        Long depositPrice,
        Boolean orderStatus,
        String orderDate
) {
    // TODO: 프로세스 ID 어떻게 할지 생각하기 -> 넣을지 말지
    public Order toDomain(
            Long userId, Long processId, Long threadId, OrderStatus orderStatus,Payment paymentToSave) {
        return Order.builder()
                .depositPrice(depositPrice)
                .actualPrice(price)
                .orderId(null)
                .threadId(threadId)
                .uuid(null)
                .payment(paymentToSave)
                .orderStatus(orderStatus)
                .build();
    }
}

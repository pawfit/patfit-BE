package com.peauty.payment.business.dto;

import com.peauty.domain.payment.*;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record OrderCommand(
        Integer cost,
        Boolean orderStatus,
        String orderDate
) {
    // TODO: 프로세스 ID 어떻게 할지 생각하기 -> 넣을지 말지
    public Order toDomain(
            Long userId, Long processId, Long threadId,
            String uuid, LocalDateTime orderDate, Payment paymentToSave) {
        return Order.builder()
                .price(cost)
                .orderId(userId)
                .threadId(threadId)
                .orderDate(orderDate)
                .orderUuid(uuid)
                .payment(paymentToSave)
                .build();
    }
}

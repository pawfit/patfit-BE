package com.peauty.domain.payment;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long threadId;
    private Long depositPrice;
    private String uuid;
    private OrderStatus orderStatus;
    private Payment payment;

    public void updateDepositPrice(Long depositPrice) {
        this.depositPrice = depositPrice;
    }

    public void updateOrderUuid() {
        this.uuid = String.valueOf(UUID.randomUUID());
    }

    public void  transferReservationCost(Long cost) {
        Double halfAmount = cost * 0.5;
        this.depositPrice = (long) Math.toIntExact(Math.round(halfAmount / 100.0) * 100);
    }

    public void updateOrderStatus() {
        this.orderStatus = OrderStatus.READY;
    }
}

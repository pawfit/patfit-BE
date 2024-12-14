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
    private Long actualPrice;
    private String uuid;
    private OrderStatus orderStatus;
    private Payment payment;

    public void updateDepositPrice(Long depositPrice) {
        this.depositPrice = depositPrice;
    }

    public void registerOrderUuid() {
        this.uuid = String.valueOf(UUID.randomUUID());
    }

    public void updateOrderToReady() {
        this.orderStatus = OrderStatus.READY;
    }

    public void updateStatusToError() {
        this.orderStatus = OrderStatus.ERROR;
    }

    public void updateOrderToCanceled() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

    public void updateValidatedPayment(Payment paymentToValidate) {
        this.payment = paymentToValidate;
    }

    public void updateOrderToCompleted() {
        this.orderStatus = OrderStatus.COMPLETED;
    }
}

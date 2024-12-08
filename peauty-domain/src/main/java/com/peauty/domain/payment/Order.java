package com.peauty.domain.payment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long threadId;
    private Integer cost;
    private Boolean isPaymentCompleted;
    private LocalDateTime orderDate;
    private String orderUuid;

    public void updateCost(Integer cost) {
        this.cost = cost;
    }

    public void updatePaymentStatus(Boolean paymentStatus) {
        this.isPaymentCompleted = paymentStatus;
    }

    public void updateOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public void updateOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void transferReservationCost(Integer cost) {
        Double halfAmount = cost * 0.5;
        // this.cost = (Integer) (Math.round(halfAmount/ 100.0) * 100);
        this.cost = Math.toIntExact(Math.round(halfAmount / 100.0) * 100);
    }

}

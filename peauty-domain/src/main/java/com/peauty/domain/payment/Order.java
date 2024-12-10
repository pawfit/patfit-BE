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
    private Long depositPrice;
    private Long grommingPrice;
    private LocalDateTime orderDate;
    private String orderUuid;
    private Payment payment;

    public void updatePrice(Long cost) {
        this.depositPrice = cost;
    }

    public void updateOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public void updateOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void transferReservationCost(Long cost) {
        Double halfAmount = cost * 0.5;
        this.depositPrice = (long) Math.toIntExact(Math.round(halfAmount / 100.0) * 100);
    }

}

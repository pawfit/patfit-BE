package com.peauty.domain.payment;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Integer cost;
    private Boolean paymentStatus;

    public void updateCost(Integer cost) {
        this.cost = cost;
    }

    public void updatePaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

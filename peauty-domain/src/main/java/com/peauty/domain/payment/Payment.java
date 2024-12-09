package com.peauty.domain.payment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long paymentId;
    private Integer price;
    private LocalDateTime paymentDate;
    private String paymentUuid;
    private PaymentStatus status;

    public static Payment initializePayment(Long userId, Object dto) {
        return Payment.builder()
                .paymentId(null)
                .price(100)
                .paymentUuid(null)
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatus.READY)
                .build();
    }

    public void validationSuccess() {

    }

    public void updatePayment() {
        this.paymentId = paymentId;
        this.price = price;
        this.status = PaymentStatus.COMPLETED;
        this.paymentDate = LocalDateTime.now();
        this.paymentUuid = paymentUuid;
    }
}

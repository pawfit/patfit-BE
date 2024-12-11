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
    private Long orderId;
    private Long depositPrice;
    private LocalDateTime paymentEventTimestamp;
    private String paymentUuid;
    private PaymentStatus status;
    private final String paymentPaidStatus = "paid";

    public static Payment initializePayment(Long depositPrice) {
        return Payment.builder()
                .paymentId(0L)
                .orderId(0L)
                .depositPrice(depositPrice)
                .paymentUuid("NOT UUID")
                .paymentEventTimestamp(LocalDateTime.now())
                .status(PaymentStatus.READY)
                .build();
    }

    // TODO 유효성 검사를 하나로 모아두는 메서드 제작!
    public void validationSuccess() {
    }

    public Boolean checkIfPaymentFailed(String paymentStatus) {
        return paymentStatus.equals(paymentPaidStatus);
    }

    public boolean checkIfPaymentPriceEquals(Long actualAmount) {
        if(actualAmount.equals(depositPrice)) {
            return true;
        }
        return false;
    }

    public void updateStatusToCancel() {
        this.status = PaymentStatus.CANCELLED;
        this.paymentEventTimestamp = LocalDateTime.now();
    }

    public void updateStatusToComplete() {
        this.status = PaymentStatus.COMPLETED;
        this.paymentEventTimestamp = LocalDateTime.now();
    }

    public void updateOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void updateUuid(String paymentUuid) {
        this.paymentUuid = paymentUuid;
    }
}

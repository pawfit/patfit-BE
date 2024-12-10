package com.peauty.domain.payment;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
    private LocalDateTime paymentDate;
    private String paymentUuid;
    private PaymentStatus status;

    private final String paymentPaidStatus = "paid";

    public static Payment initializePayment(Long depositPrice) {
        return Payment.builder()
                .paymentId(0L)
                .orderId(0L)
                .depositPrice(depositPrice)
                .paymentUuid("NOT UUID")
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatus.READY)
                .build();
    }

    public void validationSuccess() {

    }

    public void updatePayment() {
        this.paymentId = paymentId;
        this.depositPrice = depositPrice;
        this.status = PaymentStatus.COMPLETED;
        this.paymentDate = LocalDateTime.now();
        this.paymentUuid = paymentUuid;
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
    }

    public void updateStatusToComplete() {
        this.status = PaymentStatus.COMPLETED;
    }

    public void updateOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void updateUuid(String paymentUuid) {
        this.paymentUuid = paymentUuid;
    }
}

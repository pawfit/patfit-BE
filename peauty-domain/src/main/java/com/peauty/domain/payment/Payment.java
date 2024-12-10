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
    private Long depositPrice;
    private Long grommingPrice;
    private LocalDateTime paymentDate;
    private String paymentUuid;
    private PaymentStatus status;

    private final String paymentPaidStatus = "paid";

    public static Payment initializePayment(Long userId) {
        return Payment.builder()
                .paymentId(null)
                .depositPrice(0L)
                .paymentUuid(null)
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
        if(paymentStatus.equals(paymentPaidStatus)){
            return true;
        }
        return false;
    }

    public boolean checkIfPaymentPriceEquals(Integer actualAmount) {
        return actualAmount.equals(this.depositPrice);
    }

    public void updateStatusToCancel() {
        this.status = PaymentStatus.CANCELLED;
    }

    public void updateStatusToComplete() {
        this.status = PaymentStatus.COMPLETED;
    }
}

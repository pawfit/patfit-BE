package com.peauty.domain.payment;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private final String paymentPaidStatus = "paid";
    private Long paymentId;
    private Long orderId;
    private Long depositPrice;
    private LocalDateTime paymentEventTimestamp;
    private String paymentUuid;
    private PaymentStatus status;

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

    public static Payment createNewPaymentForLog(Payment payment) {
        return Payment.builder()
                .orderId(payment.orderId)
                .status(payment.status)
                .paymentEventTimestamp(payment.paymentEventTimestamp)
                .paymentUuid(payment.paymentUuid)
                .depositPrice(payment.depositPrice)
                .build();
    }

    // TODO 유효성 검사를 하나로 모아두는 메서드 제작!
    public void validationSuccess() {
    }

    public Boolean checkPortonePaymentStatusNotPaied(String paymentStatus) {
        return !Objects.equals(paymentStatus, paymentPaidStatus);
    }

    public boolean checkPaymentPriceEqualsWithPortonePrice(Long actualAmount) {
        return !Objects.equals(actualAmount, depositPrice);
    }

    public void updateStatusToCancel() {
        this.status = PaymentStatus.CANCELLED;
        this.paymentEventTimestamp = LocalDateTime.now();
    }

    public void updateStatusToError() {
        this.status = PaymentStatus.ERROR;
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

    public void validatePrice(Long actualPrice, Long estimatePrice) {
        if (actualPrice == null || estimatePrice == null) {
            throw new PeautyException(PeautyResponseCode.NOT_FOUND_ACTUAL_PRICE);
        }

        // TODO. DB에 저장한 견적서는 예약금이 아니지만 클라이언트에서 받는 값은 예약금 금액을 받기 때문에
        //       해당 유효성 검사가 정상적으로 작동하지 않는다. 해당 검사는 클라이언트에서 예약금이 아닌 실제 결제 금액
        //       을 가져올 때 사용 가능하다.
//        else if(estimatePrice != actualPrice) {
//            throw new PeautyException(PeautyResponseCode.PAYMENT_AMOUNT_NOT_EQUALS);
//        }
//        else if(estimatePrice == actualPrice) {
//            transferReservationCost(estimatePrice);
//        }
    }

    public void transferReservationCost(Long price) {
        Double depositPrice = price * 0.5;
        this.depositPrice = (long) Math.toIntExact(Math.round(depositPrice / 100.0) * 100);
    }


}

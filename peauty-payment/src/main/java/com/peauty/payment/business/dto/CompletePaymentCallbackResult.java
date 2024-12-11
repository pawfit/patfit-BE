package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Payment;
import com.peauty.domain.payment.PaymentStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompletePaymentCallbackResult(
        Long paymentId,
        Long threadId,
        Long orderId,
        String workspaceName,
        LocalDateTime paymentEventTimestamp,
        Long depositPrice,
        Long actualPrice,
        PaymentStatus paymentStatus
) {
    // 전체 값 보내주기
    public static CompletePaymentCallbackResult from(
            Payment savedPayment, String workspaceName, Long threadId, Long actualPrice) {
        return CompletePaymentCallbackResult.builder()
                .paymentId(savedPayment.getPaymentId())
                .orderId(savedPayment.getOrderId())
                .workspaceName(workspaceName)
                .threadId(threadId)
                .actualPrice(actualPrice)
                .depositPrice(savedPayment.getDepositPrice())
                .paymentEventTimestamp(savedPayment.getPaymentEventTimestamp())
                .paymentStatus(savedPayment.getStatus())
                .build();
    }
}

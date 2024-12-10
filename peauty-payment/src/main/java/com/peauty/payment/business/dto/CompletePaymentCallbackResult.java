package com.peauty.payment.business.dto;

import com.peauty.domain.payment.Payment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CompletePaymentCallbackResult(
        String workspaceName,
        String threadId,
        LocalDateTime paymentDated,
        Long price
) {
    // 전체 값 보내주기
    public static CompletePaymentCallbackResult from(Payment savedPayment) {
        return CompletePaymentCallbackResult.builder()
                .workspaceName(null)
                .threadId(null)
                .price(savedPayment.getDepositPrice())
                .paymentDated(savedPayment.getPaymentDate())
                .build();
    }
}

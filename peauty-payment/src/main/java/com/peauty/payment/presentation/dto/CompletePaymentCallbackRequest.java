package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CompletePaymentCallbackCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CompletePaymentCallbackRequest(
        @Schema(description = "주문 번호", example = "1")
        Long orderId,
        @Schema(description = "예약금(실제 결제한 금액)", example = "10000")
        Long depositPrice,
        @Schema(description = "결제 UUID (포트원에서 준 iamport_UID)", example = "sdflkj123")
        String paymentUuid
) {
    public CompletePaymentCallbackCommand toCommand() {
        return CompletePaymentCallbackCommand.builder()
                .depositPrice(depositPrice)
                .paymentUuid(paymentUuid)
                .orderId(orderId)
                .build();
    }
}

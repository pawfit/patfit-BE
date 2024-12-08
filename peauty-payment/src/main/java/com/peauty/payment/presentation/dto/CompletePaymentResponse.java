package com.peauty.payment.presentation.dto;

import com.peauty.payment.business.dto.CompletePaymentResult;

public record CompletePaymentResponse(

) {
    public static CompletePaymentResponse from(CompletePaymentResult result) {
        return null;
    }
}

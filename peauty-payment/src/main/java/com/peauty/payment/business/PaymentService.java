package com.peauty.payment.business;

import com.peauty.payment.business.dto.CompletePaymentCallbackCommand;
import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import com.peauty.payment.business.dto.OrderCommand;
import com.peauty.payment.business.dto.OrderResult;

public interface PaymentService {
    OrderResult saveOrder(
            Long userId,
            Long threadId,
            Long processId, OrderCommand command);
    CompletePaymentCallbackResult completePayment(
            Long userId,
            Long threadId,
            Long processId, CompletePaymentCallbackCommand command);
}

package com.peauty.payment.business;

import com.peauty.payment.business.dto.CompletePaymentCommand;
import com.peauty.payment.business.dto.CompletePaymentResult;
import com.peauty.payment.business.dto.OrderCommand;
import com.peauty.payment.business.dto.OrderResult;

public interface PaymentService {
    OrderResult saveOrder(
            Long userId,
            Long threadId,
            Long processId, OrderCommand command);
    CompletePaymentResult completePayment(
            Long userId,
            Long threadId,
            Long processId, CompletePaymentCommand command);
}

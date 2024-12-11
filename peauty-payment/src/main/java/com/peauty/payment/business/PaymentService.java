package com.peauty.payment.business;

import com.peauty.payment.business.dto.CompletePaymentCallbackCommand;
import com.peauty.payment.business.dto.CompletePaymentCallbackResult;
import com.peauty.payment.business.dto.CreateOrderCommand;
import com.peauty.payment.business.dto.CreateOrderResult;

public interface PaymentService {
    CreateOrderResult saveOrder(
            Long userId,
            Long threadId,
            Long processId, CreateOrderCommand command);
    CompletePaymentCallbackResult completePayment(
            Long userId,
            Long threadId,
            Long processId, CompletePaymentCallbackCommand command);
}

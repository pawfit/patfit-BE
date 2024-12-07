package com.peauty.payment.business;

import com.peauty.payment.business.dto.OrderCommand;
import com.peauty.payment.business.dto.OrderResult;

public interface PaymentService {
    OrderResult saveOrder(Long userId, Long threadId, Long processId, OrderCommand command);
    void ValidatePayment(Object command);
}

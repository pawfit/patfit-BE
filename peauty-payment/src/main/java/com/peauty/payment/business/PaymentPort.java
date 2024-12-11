package com.peauty.payment.business;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;

public interface PaymentPort {
    Order saveOrder(Order order);
    Order getById(Long userId);
    Payment getByOrderId(Long paymentId);
    Payment savePayment(Payment payment);

    String findWorkspaceNameByThreadId(Long threadId);

    Long findActualPriceByThreadId(Long threadId);
}
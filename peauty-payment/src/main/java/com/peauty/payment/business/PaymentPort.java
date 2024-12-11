package com.peauty.payment.business;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;

public interface PaymentPort {
    Order saveOrder(Order order);

    Payment getByOrderId(Long paymentId);

    Order findOrderById(Long orderId);

    Payment savePayment(Payment payment);

    String findWorkspaceNameByThreadId(Long threadId);

    Long findActualPriceByThreadId(Long threadId);
}
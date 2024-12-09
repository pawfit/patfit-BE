package com.peauty.payment.business;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;

public interface PaymentPort {
    Order saveOrder(Order order);

    Order getOrder(Long userId);

    void orderDelete(Order order);

    void paymentDelete(Order order);
    Payment getByPaymentId(Long paymentId);

    Payment savePaymentToComplete(Payment paymentToSave);
}
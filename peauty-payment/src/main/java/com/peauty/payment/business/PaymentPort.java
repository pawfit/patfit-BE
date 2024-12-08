package com.peauty.payment.business;

import com.peauty.domain.payment.Order;

public interface PaymentPort {
    Order save(Order order);

    Order getOrder(Long userId);
}

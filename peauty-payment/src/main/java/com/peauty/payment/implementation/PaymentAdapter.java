package com.peauty.payment.implementation;

import com.peauty.domain.payment.Order;
import com.peauty.payment.business.PaymentPort;
import com.peauty.persistence.payment.OrderEntity;
import com.peauty.persistence.payment.OrderRepository;
import com.peauty.persistence.payment.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentPort {

    private final OrderRepository orderRepository;

    public Order save(Order order) {
        OrderEntity orderEntityToSave = PaymentMapper.toOrderEntity(order);
        OrderEntity orderEntity = orderRepository.save(orderEntityToSave);
        return PaymentMapper.toOrderDomain(orderEntity);
    }

    @Override
    public Order getOrder(Long userId) {
        return null;
    }

    @Override
    public void orderDelete(Order order) {

    }

    @Override
    public void paymentDelete(Order order) {

    }
}

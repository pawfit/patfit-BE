package com.peauty.payment.implementation;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;
import com.peauty.payment.business.PaymentPort;
import com.peauty.persistence.payment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentPort {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public Order saveOrder(Order order) {
        OrderEntity orderEntityToSave = PaymentMapper.toOrderEntity(order);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntityToSave);

        PaymentEntity paymentEntityToSave = PaymentMapper.toPaymentEntity(order.getPayment());
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntityToSave);
        return PaymentMapper.toOrderDomain(savedOrderEntity, PaymentMapper.toPayment(savedPaymentEntity));

    }

    @Override
    public Order getOrder(Long userId) {
        return null;
    }

    @Override
    public Payment getByPaymentId(Long paymentId) {
        return null;
    }

    @Override
    public Payment savePaymentToComplete(Payment payment) {
        PaymentEntity paymentEntityToSave = PaymentMapper.toPaymentEntity(payment);
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntityToSave);

        return null;
    }
}

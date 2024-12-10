package com.peauty.payment.implementation;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;
import com.peauty.domain.response.PeautyResponseCode;
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
        order.getPayment().updateOrderId(savedOrderEntity.getId());
        Payment savedPayment = savePayment(order.getPayment());
        return PaymentMapper.toOrderDomain(savedOrderEntity, savedPayment);
    }

    @Override
    public Order getById(Long userId) {
        OrderEntity orderEntity = orderRepository.getById(userId);
        return null; //PaymentMapper.toOrderDomain(orderEntity);
    }

    @Override
    public Payment getByOrderId(Long orderId) {
        PaymentEntity paymentEntity = paymentRepository.getByOrderId(orderId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_PAYMENT));
        return PaymentMapper.toPaymentDomain(paymentEntity);
    }

    @Override
    public Payment savePayment(Payment payment) {
        PaymentEntity paymentEntityToSave = PaymentMapper.toPaymentEntity(payment);
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntityToSave);
        return PaymentMapper.toPaymentDomain(savedPaymentEntity);
    }
}

package com.peauty.persistence.payment;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;

public class PaymentMapper {

    public static OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .threadId(order.getThreadId())
                .cost(order.getPrice())
                .isPaymentCompleted(order.getIsPaymentCompleted())
                .orderDate(order.getOrderDate())
                .uuid(order.getOrderUuid())
                .build();
    }

    public static Order toOrderDomain(OrderEntity orderEntity, Payment payment) {
        return Order.builder()
                .orderId(orderEntity.getId())
                .price(orderEntity.getCost())
                .isPaymentCompleted(orderEntity.getIsPaymentCompleted())
                .threadId(orderEntity.getThreadId())
                .orderUuid(orderEntity.getUuid())
                .orderDate(orderEntity.getOrderDate())
                .payment(null)
                .build();
    }

    public static PaymentEntity toPaymentEntity(Payment payment) {
        return null;
    }

    public static Payment toPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(paymentEntity.id)
                .paymentUuid(paymentEntity.paymentUuid)
                .status(paymentEntity.paymentStatus)
                .paymentDate(paymentEntity.paymentDate)
                .build();
    }
}

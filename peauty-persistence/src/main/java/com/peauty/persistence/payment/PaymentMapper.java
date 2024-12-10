package com.peauty.persistence.payment;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;

public class PaymentMapper {

    public static OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .threadId(order.getThreadId())
                .cost(order.getDepositPrice())
                .orderDate(order.getOrderDate())
                .uuid(order.getOrderUuid())
                .build();
    }

    public static Order toOrderDomain(OrderEntity orderEntity, Payment payment) {
        return Order.builder()
                .orderId(orderEntity.getId())
                .depositPrice(orderEntity.getCost())
                .threadId(orderEntity.getThreadId())
                .orderUuid(orderEntity.getUuid())
                .orderDate(orderEntity.getOrderDate())
                .payment(null)
                .build();
    }

    public static PaymentEntity toPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getPaymentId())
                .paymentUuid(payment.getPaymentUuid())
                .paymentDate(payment.getPaymentDate())
                .price(payment.getDepositPrice())
                .paymentStatus(payment.getStatus())
                .build();
    }

    public static Payment toPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(paymentEntity.id)
                .paymentUuid(paymentEntity.paymentUuid)
                .status(paymentEntity.paymentStatus)
                .paymentDate(paymentEntity.paymentDate)
                .depositPrice(paymentEntity.price)
                .build();
    }
}

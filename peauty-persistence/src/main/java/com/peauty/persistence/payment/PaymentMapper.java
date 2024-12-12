package com.peauty.persistence.payment;

import com.peauty.domain.payment.Order;
import com.peauty.domain.payment.Payment;

public class PaymentMapper {

    public static OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .threadId(order.getThreadId())
                .depositPrice(order.getDepositPrice())
                .actualPrice(order.getActualPrice())
                .uuid(order.getUuid())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public static Order toOrderDomain(OrderEntity orderEntity, Payment payment) {
        return Order.builder()
                .orderId(orderEntity.getId())
                .depositPrice(orderEntity.getDepositPrice())
                .actualPrice(orderEntity.getActualPrice())
                .threadId(orderEntity.getThreadId())
                .uuid(orderEntity.getUuid())
                .payment(payment)
                .build();
    }

    public static PaymentEntity toPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .uuid(payment.getPaymentUuid())
                .depositPrice(payment.getDepositPrice())
                .status(payment.getStatus())
                .build();
    }

    public static Payment toPaymentDomain(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(paymentEntity.id)
                .orderId(paymentEntity.orderId)
                .paymentUuid(paymentEntity.uuid)
                .status(paymentEntity.status)
                .depositPrice(paymentEntity.depositPrice)
                .build();
    }
}

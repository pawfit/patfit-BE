package com.peauty.persistence.payment;

import com.peauty.domain.payment.Order;

public class PaymentMapper {

    public static OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .threadId(order.getThreadId())
                .cost(order.getCost())
                .paymentStatus(order.getPaymentStatus())
                .orderDate(order.getOrderDate())
                .uuid(order.getOrderUuid())
                .build();
    }

    public static Order toOrderDomain(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(orderEntity.getId())
                .cost(orderEntity.getCost())
                .paymentStatus(orderEntity.getPaymentStatus())
                .threadId(orderEntity.getThreadId())
                .orderUuid(orderEntity.getUuid())
                .orderDate(orderEntity.getOrderDate())
                .build();
    }
}

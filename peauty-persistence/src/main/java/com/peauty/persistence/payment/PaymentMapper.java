package com.peauty.persistence.payment;

import com.peauty.domain.payment.Order;

public class PaymentMapper {

    public static OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .threadId(order.getThreadId())
                .cost(order.getCost())
                .isPaymentCompleted(order.getIsPaymentCompleted())
                .orderDate(order.getOrderDate())
                .uuid(order.getOrderUuid())
                .build();
    }

    public static Order toOrderDomain(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(orderEntity.getId())
                .cost(orderEntity.getCost())
                .isPaymentCompleted(orderEntity.getIsPaymentCompleted())
                .threadId(orderEntity.getThreadId())
                .orderUuid(orderEntity.getUuid())
                .orderDate(orderEntity.getOrderDate())
                .build();
    }
}

package com.peauty.persistence.payment;

import com.peauty.domain.payment.PaymentStatus;
import com.peauty.persistence.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name= "order_id", nullable = false)
    Long orderId;

    @Column(name = "depositPrice", nullable = false)
    Long depositPrice;

    @Column(name = "uuid", nullable = false)
    String uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymxent_status", nullable = false)
    PaymentStatus status;

    public void updateOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

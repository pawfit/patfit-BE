package com.peauty.persistence.payment;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thread_id", nullable = false)
    private Long threadId;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @Column(name = "payment_status", nullable = false)
    private Boolean isPaymentCompleted;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "uuid", nullable = false)
    private String uuid;
}

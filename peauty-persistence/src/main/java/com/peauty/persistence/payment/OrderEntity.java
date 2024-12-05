package com.peauty.persistence.payment;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "payment_status", nullable = false)
    private Boolean paymentStatus;
}

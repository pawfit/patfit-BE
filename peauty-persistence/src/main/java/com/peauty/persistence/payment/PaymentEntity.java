package com.peauty.persistence.payment;

import com.peauty.domain.payment.PaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class PaymentEntity {

    @Id
    @GeneratedValue(generator = "")
    Long id;

    @Column(name = "price", nullable = false)
    Integer price;

    @Column(name = "payment_date", nullable = false)
    LocalDateTime paymentDate;

    @Column(name = "payment_uuid", nullable = false)
    String paymentUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    PaymentStatus paymentStatus;

}

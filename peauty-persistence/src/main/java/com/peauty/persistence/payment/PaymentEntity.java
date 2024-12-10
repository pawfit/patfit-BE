package com.peauty.persistence.payment;

import com.peauty.domain.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Entity(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(generator = "")
    Long id;

    @Column(name = "price", nullable = false)
    Long price;

    @Column(name = "payment_date", nullable = false)
    LocalDateTime paymentDate;

    @Column(name = "payment_uuid", nullable = false)
    String paymentUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    PaymentStatus paymentStatus;

}

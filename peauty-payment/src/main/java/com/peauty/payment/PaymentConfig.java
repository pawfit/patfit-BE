package com.peauty.payment;


import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                "com.peauty.domain",
                "com.peauty.persistence",
                "com.peauty.payment"
        }
)
public class PaymentConfig {

}

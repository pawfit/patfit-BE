package com.peauty.payment.presentation;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    private final PaymentProperties paymentProperties;

    public PaymentConfig(PaymentProperties paymentProperties) {
        this.paymentProperties = paymentProperties;
    }

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(
                paymentProperties.getApiKey(),
                paymentProperties.getApiSecret()
        );
    }
}
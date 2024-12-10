package com.peauty.customer.presentation.config;


import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO: payment 패키지에서 관리할 수 있도록 리팩토링
@Configuration
public class PaymentConfig {
    @Bean
    public IamportClient iamportClient() {
        String apiKey = "${IAMPORT_API_KEY}";
        String apiSecret = "${IAMPORT_API_SECRET_KEY}";
        return new IamportClient(apiKey, apiSecret);
    }
}

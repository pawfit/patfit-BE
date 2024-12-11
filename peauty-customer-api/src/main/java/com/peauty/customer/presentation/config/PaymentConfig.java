package com.peauty.customer.presentation.config;


import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO: payment 패키지에서 관리할 수 있도록 리팩토링
@Configuration
public class PaymentConfig {

    @Value("${iamport.api-key}")
    private String apiKey;

    @Value("${iamport.api-secret-key}")
    private String apiSecretKey;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, apiSecretKey);
    }
}

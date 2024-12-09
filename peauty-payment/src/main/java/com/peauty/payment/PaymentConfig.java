package com.peauty.payment;


import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(
        basePackages = {
                "com.peauty.domain",
                "com.peauty.persistence",
                "com.peauty.payment"
        }
)

@Configuration
public class PaymentConfig {
        @Bean
        public IamportClient iamportClient() {
                String apiKey = "${IAMPORT_API_KEY}";
                String apiSecret = "${IAMPORT_API_SECRET_KEY}";
                return new IamportClient(apiKey, apiSecret);
        }
}

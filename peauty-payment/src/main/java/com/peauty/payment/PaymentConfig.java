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
                String apiKey = "4872684828305477";
                String apiSecret = "lXOXhJGVQOxLwvOkij5GUMNSByXfsFmla2EQSD6PgJGr76NLfvirFxUScqf8cGToSjvKJtB8xKntCQr8";
                return new IamportClient(apiKey, apiSecret);
        }

}

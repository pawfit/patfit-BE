package com.peauty.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.peauty.persistence")
@EnableJpaRepositories("com.peauty.persistence")
@SpringBootApplication(
        scanBasePackages = {
                "com.peauty.domain",
                "com.peauty.logging",
                "com.peauty.persistence",
                "com.peauty.auth",
                "com.peauty.cache",
                "com.peauty.aws",
                "com.peauty.customer",
                "com.peauty.payment"
        }
)
public class PeautyCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeautyCustomerApplication.class, args);
    }

}

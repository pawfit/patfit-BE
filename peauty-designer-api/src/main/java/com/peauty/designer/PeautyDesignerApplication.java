package com.peauty.designer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.peauty.domain",
                "com.peauty.logging",
                "com.peauty.persistence",
                "com.peauty.auth",
                "com.peauty.cache",
                "com.peauty.aws",
                "com.peauty.designer"
        }
)
public class PeautyDesignerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeautyDesignerApplication.class, args);
    }

}

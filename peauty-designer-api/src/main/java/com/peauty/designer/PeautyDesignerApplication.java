package com.peauty.designer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {
				"com.peauty.domain",
				"com.peauty.logging",
				"com.peauty.persistence",
		}
)
public class PeautyDesignerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeautyDesignerApplication.class, args);
	}

}

package com.szcgc.agreement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@SpringBootApplication
@EntityScan(value = "com.szcgc.agreement", basePackageClasses = Jsr310JpaConverters.class)
public class AgreementApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgreementApplication.class, args);
	}
}

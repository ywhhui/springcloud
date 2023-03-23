package com.szcgc.customer;

import com.szcgc.comm.web.IbcArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * @Author liaohong
 * @create 2022/9/14 17:31
 */
@EnableFeignClients(basePackages = {"com.szcgc"})
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(value = {"com.szcgc"}, basePackageClasses = IbcArgumentResolver.class)
@EntityScan(value = "com.szcgc.customer", basePackageClasses = Jsr310JpaConverters.class)
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}

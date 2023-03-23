package com.szcgc.finance;

import com.szcgc.comm.web.IbcArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * 财务信息
 *
 * @Author chenjiaming
 * @create 2022-9-22 21:31:30
 */
@EnableFeignClients(basePackages = {"com.szcgc"})
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(value = {"com.szcgc"}, basePackageClasses = IbcArgumentResolver.class)
@EntityScan(value = "com.szcgc", basePackageClasses = Jsr310JpaConverters.class)
public class FinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceApplication.class, args);
    }

}

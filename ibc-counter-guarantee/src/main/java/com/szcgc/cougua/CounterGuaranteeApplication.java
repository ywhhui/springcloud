package com.szcgc.cougua;

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
 * @create 2022/9/14 15:28
 */
@EnableFeignClients("com.szcgc")
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(value = {"com.szcgc.comm","com.szcgc.cougua","com.szcgc.message.send"}, basePackageClasses= IbcArgumentResolver.class)
@EntityScan(value = {"com.szcgc.comm","com.szcgc.cougua"}, basePackageClasses = Jsr310JpaConverters.class)
public class CounterGuaranteeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CounterGuaranteeApplication.class, args);
    }

}

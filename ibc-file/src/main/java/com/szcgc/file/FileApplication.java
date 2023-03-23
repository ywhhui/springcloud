package com.szcgc.file;

import com.szcgc.comm.web.IbcArgumentResolver;
import com.szcgc.file.jpa.PathAttributeConvert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;

/**
 * @Author liaohong
 * @create 2022/9/16 15:28
 */
@SpringBootApplication
@ComponentScan(value = "com.szcgc.file", basePackageClasses = IbcArgumentResolver.class)
@EntityScan(value = "com.szcgc.file", basePackageClasses = Jsr310JpaConverters.class)
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}

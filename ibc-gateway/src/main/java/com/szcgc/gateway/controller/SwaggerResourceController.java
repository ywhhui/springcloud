package com.szcgc.gateway.controller;

import com.szcgc.gateway.config.SwaggerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.*;

import java.util.List;

/**
 * @Author liaohong
 * @create 2022/9/20 9:39
 */
//@RestController
//@RequestMapping("/swagger-resources")
public class SwaggerResourceController {

//    private SwaggerProvider swaggerResourceProvider;
//
//    @Autowired
//    public SwaggerResourceController(SwaggerProvider swaggerResourceProvider) {
//        this.swaggerResourceProvider = swaggerResourceProvider;
//    }
//
//    @RequestMapping(value = "/configuration/security")
//    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
//        return new ResponseEntity<>(SecurityConfigurationBuilder.builder().build(), HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/configuration/ui")
//    public ResponseEntity<UiConfiguration> uiConfiguration() {
//        return new ResponseEntity<>(UiConfigurationBuilder.builder().build(), HttpStatus.OK);
//    }
//
//    @RequestMapping
//    public ResponseEntity<List<SwaggerResource>> swaggerResources() {
//        return new ResponseEntity<>(swaggerResourceProvider.get(), HttpStatus.OK);
//    }
}

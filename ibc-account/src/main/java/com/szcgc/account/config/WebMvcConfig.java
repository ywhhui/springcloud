package com.szcgc.account.config;

import com.szcgc.comm.web.IbcArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/8/24 19:17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    IbcArgumentResolver ibcArgumentResolver;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(authorizateInterceptor);
//        registration.excludePathPatterns(WebConstants.URL_API, WebConstants.URL_LOGIN, WebConstants.URL_LOGIN_CHECK, "/springdoc/**", "/test/**");
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(ibcArgumentResolver);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("*")
//                .allowedHeaders("accessToken");
//        //System.out.println("addCorsMappings done");
//    }

    //TODO 有这段代码无法跑测试，所以暂时屏蔽
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        //这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
//        return new ServerEndpointExporter();
//    }
}

package com.lubycon.ourney.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
/*
    private String[] INTERCEPTOR_WHITE_LIST = {
            "/users/signUp",
            "/users/signIn",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/oauth/**",
            "/kakao/**",
            "/users/login",
            "/users/login/oauth"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new JwtAuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(INTERCEPTOR_WHITE_LIST);
    }

    // Swagger 허용
    @Override public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
*/
}

package com.lubycon.ourney.common.config;

import com.lubycon.ourney.common.config.interceptor.JwtAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Slf4j
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthInterceptor jwtAuthInterceptor;

    public WebConfig(JwtAuthInterceptor jwtAuthInterceptor) {
        this.jwtAuthInterceptor = jwtAuthInterceptor;
    }

    private String[] INTERCEPTOR_WHITE_LIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/oauth/**",
            "/kakao/**",
            "/users/login",
            "/users/login/oauth",
    };
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        log.info("#########INTERCEPTOR##########");
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/trips**")
                .excludePathPatterns(INTERCEPTOR_WHITE_LIST);
    }

    // Swagger 허용
    @Override public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST")
                .exposedHeaders("jwtAccessToken","jwtRefreshToken")
                .maxAge(3000);
    }
}

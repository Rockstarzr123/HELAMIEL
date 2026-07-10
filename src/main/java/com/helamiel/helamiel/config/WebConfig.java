package com.helamiel.helamiel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registra el {@link SesionInterceptor} para proteger las paginas del
 * panel administrativo, excluyendo el login, la API REST y los recursos
 * estaticos (CSS/JS), que deben quedar siempre accesibles.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SesionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/logout",
                        "/api/**",
                        "/css/**",
                        "/js/**",
                        "/webjars/**"
                );
    }
}

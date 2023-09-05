package com.example.proj.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class CorsConfig extends CorsFilter {
    public CorsConfig() {
        super(corsConfigurationSource());
    }

    private static UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许的域名，可以设置为具体的域名，或者使用通配符 * 表示允许任意域名访问
        config.addAllowedOrigin("http://localhost:3000");
        // 允许的请求方法，如 GET、POST 等
        config.addAllowedMethod("*");
        // 允许的请求头
        config.addAllowedHeader("*");
        // 是否允许发送Cookie信息
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return source;
    }


}

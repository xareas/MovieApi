package com.jonareas.movies;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
//import org.modelmapper.ModelMapper;

@SpringBootApplication
public class MoviesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {

        // based on https://github.com/spring-projects/spring-boot/issues/5834
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        config.setAllowedOriginPatterns(Collections.singletonList("*"));

        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);

        return bean;
    }

}

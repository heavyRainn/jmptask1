package com.jmp.epam.one.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("com.jmp.epam")
@PropertySource("classpath:one.properties")
public class SpringConfig {

    @Bean("indexes")
    public Map<String, String> hashMap() {
        return new HashMap<>();
    }

}

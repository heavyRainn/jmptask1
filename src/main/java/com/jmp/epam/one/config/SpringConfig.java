package com.jmp.epam.one.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.jmp.epam")
@PropertySource("classpath:one.properties")
public class SpringConfig {
}

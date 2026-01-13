package com.soundbar91.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA 설정
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.soundbar91.infrastructure")
public class JpaConfig {
}

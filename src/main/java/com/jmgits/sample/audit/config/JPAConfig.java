package com.jmgits.sample.audit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.history.RevisionRepository;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@EnableJpaRepositories(basePackages = "com.jmgits.sample.audit",
        excludeFilters = @ComponentScan.Filter(type = ASSIGNABLE_TYPE, value = RevisionRepository.class)
)
public class JPAConfig {
}
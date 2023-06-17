package com.craftsoft.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.craftsoft.main.db.repository")
public class DatasourceConfiguration {

}

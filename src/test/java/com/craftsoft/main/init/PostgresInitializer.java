package com.craftsoft.main.init;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String IMAGE = "postgres:latest";
    private static final String TEST_DATABASE = "craftsoft_test";
    private static final String USER_NAME = "craftsoft_test";
    private static final String PASSWORD = "craftsoft_test";
    private static final Integer PORT = 5432;

    private final PostgreSQLContainer postgresContainer = new PostgreSQLContainer<>(IMAGE)
            .withReuse(true)
            .withDatabaseName(TEST_DATABASE)
            .withUsername(USER_NAME)
            .withPassword(PASSWORD)
            .withExposedPorts(PORT);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        postgresContainer.start();
        postgresContainer.getMappedPort(PORT);

        TestPropertyValues.of(
                String.format(
                        "database.port=%s",
                        postgresContainer.getMappedPort(PORT))
        ).applyTo(applicationContext.getEnvironment());
    }
}

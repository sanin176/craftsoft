package com.craftsoft.main;

import com.craftsoft.main.init.PostgresInitializer;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(
        initializers = {PostgresInitializer.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FlywayTest(locationsForMigrate = {
        "classpath:db/migration",
        "test/resources/db/migration"
})
@AutoConfigureWebTestClient(timeout = "10000")
@AutoConfigureMockMvc
public class CraftsoftApplicationTests {

}

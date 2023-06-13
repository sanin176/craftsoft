package com.craftsoft.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
class TestCraftsoftApplication {

    public static void main(String[] args) {
        SpringApplication.from(CraftsoftApplication::main).with(TestCraftsoftApplication.class).run(args);
    }

}

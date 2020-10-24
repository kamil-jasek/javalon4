package pl.sda.spring.customers.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PrimitiveConfig {

    @Bean
    String defaultCustomerName() {
        return "Stefan";
    }
}

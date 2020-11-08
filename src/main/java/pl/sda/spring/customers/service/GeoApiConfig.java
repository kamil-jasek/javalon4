package pl.sda.spring.customers.service;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GeoApiConfig {

    @Bean
    GeoApiContext geoApiContext(@Value("${geoapi.key}") String geoApiKey) {
        return new GeoApiContext.Builder()
            .apiKey(geoApiKey)
            .build();
    }
}

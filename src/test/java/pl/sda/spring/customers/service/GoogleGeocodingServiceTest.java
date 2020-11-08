package pl.sda.spring.customers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.spring.customers.entity.Address;

@SpringBootTest
final class GoogleGeocodingServiceTest {

    @Autowired
    private GoogleGeocodingService geocodingService;

    @Test
    void testShouldGetAddress() {
        // given
        final var latitude = 52.257502;
        final var longitude = 21.102816;

        // when
        final var address = geocodingService.reverse(latitude, longitude);

        // then
        assertNotNull(address);
        assertEquals("Dudziarska 4/2", address.getStreet());
        assertEquals("Warszawa", address.getCity());
        assertEquals("04-317", address.getZipCode());
        assertEquals("PL", address.getCountry());
    }
}

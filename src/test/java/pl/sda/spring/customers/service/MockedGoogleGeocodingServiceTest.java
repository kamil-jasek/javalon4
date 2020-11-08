package pl.sda.spring.customers.service;

import static com.google.maps.model.AddressComponentType.COUNTRY;
import static com.google.maps.model.AddressComponentType.LOCALITY;
import static com.google.maps.model.AddressComponentType.POSTAL_CODE;
import static com.google.maps.model.AddressComponentType.ROUTE;
import static com.google.maps.model.AddressComponentType.STREET_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
final class MockedGoogleGeocodingServiceTest {

    @Autowired
    private GoogleGeocodingService geocodingService;

    @MockBean
    private GoogleGeocodingReverseProxy reverseProxy;

    @Test
    void testShouldGetAddress() throws Exception {
        // given
        final var latitude = 41.0;
        final var longitude = 42.0;

        final var result = new GeocodingResult();

        final var streetComponent = new AddressComponent();
        streetComponent.shortName = "str";
        streetComponent.types = new AddressComponentType[] {ROUTE};

        final var streetNumberComponent = new AddressComponent();
        streetNumberComponent.shortName = "1";
        streetNumberComponent.types = new AddressComponentType[] {STREET_NUMBER};

        final var cityComponent = new AddressComponent();
        cityComponent.shortName = "city";
        cityComponent.types = new AddressComponentType[] {LOCALITY};

        final var zipCodeComponent = new AddressComponent();
        zipCodeComponent.shortName = "01-200";
        zipCodeComponent.types = new AddressComponentType[] {POSTAL_CODE};

        final var countryComponent = new AddressComponent();
        countryComponent.shortName = "PL";
        countryComponent.types = new AddressComponentType[] {COUNTRY};

        result.addressComponents = new AddressComponent[] {
            streetComponent, streetNumberComponent, cityComponent, zipCodeComponent, countryComponent
        };

        when(reverseProxy.reverse(any(), any())).thenReturn(new GeocodingResult[] {result});

        // when
        final var address = geocodingService.reverse(latitude, longitude);

        // then
        assertNotNull(address);
        assertEquals("str 1", address.getStreet());
        assertEquals("city", address.getCity());
        assertEquals("01-200", address.getZipCode());
        assertEquals("PL", address.getCountry());
    }
}

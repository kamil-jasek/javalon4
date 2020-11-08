package pl.sda.spring.customers.service;

import static com.google.maps.model.AddressComponentType.COUNTRY;
import static com.google.maps.model.AddressComponentType.LOCALITY;
import static com.google.maps.model.AddressComponentType.POSTAL_CODE;
import static com.google.maps.model.AddressComponentType.ROUTE;
import static com.google.maps.model.AddressComponentType.STREET_NUMBER;
import static java.util.Arrays.asList;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Service;
import pl.sda.spring.customers.entity.Address;

@Service
final class GoogleGeocodingService implements GeocodingService {

    private final GeoApiContext context;

    GoogleGeocodingService(GeoApiContext context) {
        this.context = context;
    }

    @Override
    public Address reverse(double latitude, double longitude) {
        try {
            final var results = GeocodingApi
                .reverseGeocode(context, new LatLng(latitude, longitude))
                .await();

            if (results.length == 0) {
                return null;
            }

            final var result =  results[0];
            final var components = result.addressComponents;
            final var street = findValue(ROUTE, components);
            final var streetNumber = findValue(STREET_NUMBER, components);
            final var city = findValue(LOCALITY, components);
            final var zipCode = findValue(POSTAL_CODE, components);
            final var country = findValue(COUNTRY, components);

            return new Address(street + " " + streetNumber, city, zipCode, country);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String findValue(AddressComponentType type, AddressComponent[] components) {
        for (AddressComponent component : components) {
            if (asList(component.types).contains(type)) {
                return component.shortName;
            }
        }
        return null;
    }
}

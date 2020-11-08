package pl.sda.spring.customers.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
class GoogleGeocodingReverseProxy {

    GeocodingResult[] reverse(GeoApiContext context, LatLng latLng)
        throws InterruptedException, ApiException, IOException {
        return GeocodingApi
            .reverseGeocode(context, latLng)
            .await();
    }
}

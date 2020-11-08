package pl.sda.spring.customers.dto;

import java.util.Objects;
import java.util.UUID;

public final class AddAddressFromCoordinatesDto {

    private final UUID customerId;
    private final double latitude;
    private final double longitude;

    public AddAddressFromCoordinatesDto(UUID customerId, double latitude, double longitude) {
        this.customerId = customerId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddAddressFromCoordinatesDto that = (AddAddressFromCoordinatesDto) o;
        return Double.compare(that.latitude, latitude) == 0 &&
            Double.compare(that.longitude, longitude) == 0 &&
            customerId.equals(that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, latitude, longitude);
    }
}

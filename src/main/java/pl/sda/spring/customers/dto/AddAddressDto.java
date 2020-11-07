package pl.sda.spring.customers.dto;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.Objects;
import java.util.UUID;

public final class AddAddressDto {

    private final UUID customerId;
    private final String street;
    private final String city;
    private final String zipCode;
    private final String country;

    public AddAddressDto(UUID customerId, String street, String city, String zipCode, String country) {
        requireNonNull(customerId, street, city, zipCode, country);
        this.customerId = customerId;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddAddressDto that = (AddAddressDto) o;
        return Objects.equals(customerId, that.customerId) &&
            Objects.equals(street, that.street) &&
            Objects.equals(city, that.city) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, street, city, zipCode, country);
    }
}

package pl.sda.spring.customers.dto;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;
import java.util.UUID;

public final class AddressDto {

    private final UUID id;
    private final String street;
    private final String city;
    private final String zipCode;
    private final String country;

    public AddressDto(UUID id, String street, String city, String zipCode, String country) {
        requireNonNull(id, street, city, zipCode, country);
        this.id = id;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public UUID getId() {
        return id;
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
        AddressDto that = (AddressDto) o;
        return id.equals(that.id) &&
            street.equals(that.street) &&
            city.equals(that.city) &&
            zipCode.equals(that.zipCode) &&
            country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, zipCode, country);
    }
}

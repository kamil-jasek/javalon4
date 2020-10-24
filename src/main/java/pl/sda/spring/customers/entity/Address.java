package pl.sda.spring.customers.entity;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import pl.sda.spring.customers.util.OnlyForJpa;

@Entity
@Table(name = "addresses")
public final class Address {

    @Id
    private UUID id;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    @OnlyForJpa
    private Address() {}

    public Address(String street, String city, String zipCode, String country) {
        requireNonNull(street, city, zipCode, country);
        this.id = UUID.randomUUID();
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
        Address address = (Address) o;
        return id.equals(address.id) &&
            street.equals(address.street) &&
            city.equals(address.city) &&
            zipCode.equals(address.zipCode) &&
            country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, zipCode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", street='" + street + '\'' +
            ", city='" + city + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", country='" + country + '\'' +
            '}';
    }
}

package pl.sda.spring.customers.entity;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import pl.sda.spring.customers.util.OnlyForJpa;

@Entity
@Table(name = "customers")
public final class Customer {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private String pesel;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Address> addresses;

    @OnlyForJpa
    private Customer() {}

    public Customer(String firstName, String lastName, String pesel) {
        requireNonNull(firstName, lastName, pesel);
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.addresses = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public boolean addAddress(Address address) {
        if (!addresses.contains(address)) {
            addresses.add(address);
            return true;
        }
        return false;
    }

    public boolean removeAddress(Address address) {
        return addresses.remove(address);
    }

    public List<Address> getAddresses() {
        return new ArrayList<>(addresses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return id.equals(customer.id) &&
            firstName.equals(customer.firstName) &&
            lastName.equals(customer.lastName) &&
            pesel.equals(customer.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, pesel);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", pesel='" + pesel + '\'' +
            '}';
    }
}

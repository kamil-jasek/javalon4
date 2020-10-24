package pl.sda.spring.customers.entity;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public final class Customer {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private String pesel;

    public Customer(String firstName, String lastName, String pesel) {
        requireNonNull(firstName, lastName, pesel);
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
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

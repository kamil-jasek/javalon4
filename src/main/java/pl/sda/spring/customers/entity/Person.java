package pl.sda.spring.customers.entity;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import pl.sda.spring.customers.util.OnlyForJpa;

@Entity
@DiscriminatorValue("PERSON")
@MappedSuperclass
public final class Person extends Customer {

    private String firstName;

    private String lastName;

    private String pesel;

    @OnlyForJpa
    private Person() {}

    public Person(String firstName, String lastName, String pesel) {
        requireNonNull(firstName, lastName, pesel);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
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
        if (!super.equals(o)) {
            return false;
        }
        Person person = (Person) o;
        return firstName.equals(person.firstName) &&
            lastName.equals(person.lastName) &&
            pesel.equals(person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, pesel);
    }
}

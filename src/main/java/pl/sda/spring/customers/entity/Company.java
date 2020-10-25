package pl.sda.spring.customers.entity;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import pl.sda.spring.customers.util.OnlyForJpa;

@Entity
@DiscriminatorValue("COMPANY")
public final class Company extends Customer {

    private String name;
    private String vatNumber;

    @OnlyForJpa
    private Company() {}

    public Company(String name, String vatNumber) {
        requireNonNull(name, vatNumber);
        this.name = name;
        this.vatNumber = vatNumber;
    }

    public String getName() {
        return name;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void updateName(String name) {
        requireNonNull(name);
        this.name = name;
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
        Company company = (Company) o;
        return name.equals(company.name) &&
            vatNumber.equals(company.vatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, vatNumber);
    }

    @Override
    public String toString() {
        return "Company{" +
            "name='" + name + '\'' +
            ", vatNumber='" + vatNumber + '\'' +
            '}';
    }
}

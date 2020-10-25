package pl.sda.spring.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Company findByVatNumber(String vatNumber);

    List<Customer> findByAddressesCity(String city);

    @Query("from Person p join p.addresses a where lower(p.lastName) = lower(:lastname) and a.country = upper(:country)")
    List<Person> findByLastnameFromCountry(String lastname, String country);

    @Query("from Customer p join p.addresses a where a.zipCode = :zipCode and a.country = upper(:country)")
    List<Customer> findByAllCustomersFromZipCodeAndCountry(String zipCode, String country);
}

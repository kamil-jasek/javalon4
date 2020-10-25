package pl.sda.spring.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Company findByVatNumber(String vatNumber);

    List<Customer> findByAddressesCity(String city);

    @Query("from Person p join p.addresses a where p.lastName = :lastname and a.country = :country")
    List<Person> findByLastnameFromCountry(String lastname, String country);
}

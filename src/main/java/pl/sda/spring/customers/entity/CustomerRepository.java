package pl.sda.spring.customers.entity;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Company findByVatNumber(String vatNumber);

    List<Customer> findByAddressesCity(String city);
}

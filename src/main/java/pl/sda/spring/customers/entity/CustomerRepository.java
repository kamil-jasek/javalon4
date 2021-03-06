package pl.sda.spring.customers.entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

    Optional<Company> findByVatNumber(String vatNumber);

    List<Customer> findByAddressesCity(String city);

    @Query("from Person p join p.addresses a where lower(p.lastName) = lower(:lastname) and a.country = upper(:country)")
    List<Person> findByLastnameFromCountry(String lastname, String country);

    @Query("from Customer p join p.addresses a where a.zipCode = :zipCode and a.country = upper(:country)")
    List<Customer> findByAllCustomersFromZipCodeAndCountry(String zipCode, String country);

    @Query(value = "select a.city, count(c.id) from customers c "
        + "inner join addresses a on a.customer_id = c.id "
        + "where a.country = ?1 group by a.city", nativeQuery = true)
    List<Object[]> countCustomersByCity(String country);

    @Query(value = "select a.city as city, count(c.id) as count from customers c "
        + "inner join addresses a on a.customer_id = c.id "
        + "where a.country = ?1 group by a.city", nativeQuery = true)
    List<CityCount> countByCity(String country);

    Person findByPesel(String pesel);

    interface CityCount {
        String getCity();
        int getCount();
    }

    @Query(value = "select count(a.street) as count, a.zip_code as zipCode, a.country as country "
        + "from addresses a group by a.zip_code, a.country order by count(a.street) desc",
        nativeQuery = true)
    List<StreetByZipCodeCountry> countStreetByZipCodeCountry();

    interface StreetByZipCodeCountry {
        int getCount();
        String getZipCode();
        String getCountry();
    }

    @Query("from Person where id = :id")
    Person findPersonById(UUID id);

    @Modifying
    @Query("update Person set firstName = :firstName, lastName = :lastName where id = :id")
    int updatePersonName(UUID id, String firstName, String lastName);
}

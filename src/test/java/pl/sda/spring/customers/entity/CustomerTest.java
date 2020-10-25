package pl.sda.spring.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CustomerTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void testCreatePerson() {
        // given:
        final var customer = new Person("Jan", "Nowak", "02939499393");
        final var address = new Address("Testowa", "Test", "02-304", "PL");
        customer.addAddress(address);

        // when:
        saveAndClear(customer);

        // then:
        final var readCustomer = repository.getOne(customer.getId());
        assertEquals(readCustomer, customer);
        assertEquals(address, readCustomer.getAddresses().get(0));
    }

    @Test
    @Transactional
    void testUpdateCompany() {
        // given:
        final var company = new Company("TEST S.A.", "PL3939939");
        saveAndClear(company);

        // when:
        company.updateName("TESTOWO S.A.");
        saveAndClear(company);

        // then:
        final var readCompany = repository.getOne(company.getId());
        assertEquals(readCompany, company);
    }

    @Test
    @Transactional
    void testFindCompanyByVat() {
        // given:
        saveAndClear(new Company("TEST1", "PL0303003"));
        saveAndClear(new Company("TEST2", "PL9292992"));

        // when:
        final var customer = repository.findByVatNumber("PL9292992");

        // then:
        assertEquals("PL9292992", customer.getVatNumber());
    }

    @Test
    @Transactional
    void testFindCustomersByCity() {
        // given
        final var customer1 = new Company("TEST S.A.", "PL9393939");
        customer1.addAddress(new Address("street", "Cracow", "01-600", "PL"));
        customer1.addAddress(new Address("street", "Warsaw", "01-230", "PL"));

        final var customer2 = new Company("TEST 2", "PL39933020");
        customer2.addAddress(new Address("str", "London", "03-122", "UK"));

        saveAndClear(customer1, customer2);

        // when
        final var customers = repository.findByAddressesCity("Warsaw");

        // then
        assertFalse(customers.isEmpty());
        assertTrue(customers.stream()
            .allMatch(c -> c.getAddresses().stream()
                .anyMatch(address -> address.getCity().equals("Warsaw"))));
    }

    @Test
    @Transactional
    void testFindPersonByLastnameFromCountry() {
        // given
        final var person1 = new Person("Jan", "Nowak", "0202030030");
        person1.addAddress(new Address("str1", "Warsaw", "03-230", "PL"));
        person1.addAddress(new Address("str2", "Cracow", "04-202", "PL"));
        final var person2 = new Person("Adam", "Jacek", "2883903003");
        person2.addAddress(new Address("str2", "Londo", "04-222", "UK"));

        saveAndClear(person1, person2);

        // when
        final var customers = repository.findByLastnameFromCountry("jacek", "uk");

        // then
        assertFalse(customers.isEmpty());
        assertTrue(customers.stream()
            .allMatch(person -> person.getLastName().equals("Jacek") && person.getAddresses().stream()
                .anyMatch(address -> address.getCountry().equals("UK"))));
    }

    @Test
    @Transactional
    void testFindAllCustomersFromZipCodeAndCountry() {
        // given - create two customers, add addresses
        final var zipCode = "01-300";
        final var country = "PL";
        final var company = new Company("TEST", "PL939399393");
        company.addAddress(new Address("str1", "cit1", zipCode, country));
        final var person = new Person("Jan", "Nowak", "PL93999333");
        person.addAddress(new Address("str2", "cit2", "01-240", "UK"));

        saveAndClear(company, person);

        // when
        final List<Customer> customers = repository.findByAllCustomersFromZipCodeAndCountry(zipCode, country);

        // then
        assertFalse(customers.isEmpty());
        assertTrue(customers.stream()
            .allMatch(customer -> customer.getAddresses().stream()
                .anyMatch(address -> address.getZipCode().equals(zipCode) && address.getCountry().equals(country))));
    }

    private void saveAndClear(Customer... args) {
        for (Customer customer : args) {
            repository.saveAndFlush(customer);
        }
        em.clear();
    }
}

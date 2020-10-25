package pl.sda.spring.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        customer1.addAddress(new Address("street", "Warsaw", "01-230", "PL"));
        saveAndClear(customer1);

        final var customer2 = new Company("TEST 2", "PL39933020");
        customer2.addAddress(new Address("str", "London", "03-122", "UK"));
        saveAndClear(customer2);

        // when
        final var customers = repository.findByAddressesCity("Warsaw");

        // then
        assertTrue(customers.stream()
            .allMatch(c -> c.getAddresses().stream()
                .anyMatch(address -> address.getCity().equals("Warsaw"))));
    }

    private void saveAndClear(Customer customer) {
        repository.saveAndFlush(customer);
        em.clear();
    }
}

package pl.sda.spring.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private void saveAndClear(Customer customer) {
        repository.saveAndFlush(customer);
        em.clear();
    }
}

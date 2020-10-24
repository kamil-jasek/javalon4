package pl.sda.spring.customers.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CustomerTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void testCreateCustomer() {
        // given:
        final var customer = new Customer("Jan", "Nowak", "02939499393");

        // when:
        em.persist(customer);
        em.flush();
        em.clear();

        // then:
        final var readCustomer = em.find(Customer.class, customer.getId());
        assertEquals(readCustomer, customer);
    }
}

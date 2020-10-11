package pl.sda.spring.customers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerService secondCustomerService;

    @Test
    void testCreateCustomer() {
        assertNotNull(customerService);
    }

    @Test
    void testIsSingleton() {
        assertEquals(customerService, secondCustomerService);
    }
}

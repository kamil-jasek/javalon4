package pl.sda.spring.customers.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void testCreateOrder() {
        Assertions.assertNotNull(orderService);
        orderService.createOrder("test", 12, "test");
    }
}

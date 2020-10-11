package pl.sda.spring.customers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderService {

    private final CustomerService customerService;

    public void createOrder(String productName, double price, String customerName) {
        customerService.createCustomer(customerName);
        System.out.println("Create order: " + productName + ", price: " + price);
    }
}

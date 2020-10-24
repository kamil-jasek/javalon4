package pl.sda.spring.customers.service;

import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private final CustomerService customerService;

    // @Autowired - optional
    public OrderService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void createOrder(String productName, double price, String customerName) {
        customerService.createCustomer(customerName);
        System.out.println("Create order: " + productName + ", price: " + price);
    }
}

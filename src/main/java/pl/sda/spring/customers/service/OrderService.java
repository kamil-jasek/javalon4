package pl.sda.spring.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class OrderService {

    private final CustomerService customerService;
    private final int maxCustomers;

    // @Autowired - optional
    @Autowired
    public OrderService(CustomerService customerService, @Value("${customers.max:10}") int maxCustomers) {
        this.customerService = customerService;
        this.maxCustomers = maxCustomers;
    }

    public void createOrder(String productName, double price, String customerName) {
        customerService.createCustomer(customerName);
        System.out.println("Create order: " + productName + ", price: " + price);
    }

    public int getMaxCustomers() {
        return maxCustomers;
    }
}

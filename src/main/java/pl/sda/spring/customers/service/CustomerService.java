package pl.sda.spring.customers.service;

import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    public void createCustomer(String name) {
        System.out.println("creating customer: " + name);
    }
}

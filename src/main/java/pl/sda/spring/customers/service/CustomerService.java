package pl.sda.spring.customers.service;

import org.springframework.stereotype.Component;

@Component
public final class CustomerService {

    private final String defaultCustomerName;

    public CustomerService(String defaultCustomerName) {
        this.defaultCustomerName = defaultCustomerName;
    }

    public void createCustomer(String name) {
        System.out.println("creating customer: " + name);
    }

    public String getDefaultCustomerName() {
        return defaultCustomerName;
    }
}

package pl.sda.spring.customers.service;

import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.spring.customers.dto.AddAddressDto;
import pl.sda.spring.customers.dto.AddressDto;
import pl.sda.spring.customers.dto.CompanyDto;
import pl.sda.spring.customers.dto.CreateCompanyDto;
import pl.sda.spring.customers.dto.CreatePersonDto;
import pl.sda.spring.customers.dto.PersonDto;
import pl.sda.spring.customers.entity.Address;
import pl.sda.spring.customers.entity.CustomerRepository;
import pl.sda.spring.customers.entity.Person;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    PersonDto createCustomer(CreatePersonDto dto) {
        final var person = repository.findByPesel(dto.getPesel());
        if (person != null) {
            throw new IllegalArgumentException("Person with PESEL: " + dto.getPesel() + " already exists.");
        }
        final var newPerson = repository.save(new Person(dto.getFirstName(), dto.getLastName(), dto.getPesel()));
        return new PersonDto(newPerson.getId(),
            Collections.emptyList(),
            newPerson.getFirstName(),
            newPerson.getLastName(),
            newPerson.getPesel());
    }

    @Transactional
    CompanyDto createCustomer(CreateCompanyDto dto) {
        // TODO - implement
        return null;
    }

    @Transactional
    AddressDto addAddress(AddAddressDto dto) {
        final var customer = repository.getOne(dto.getCustomerId());
        final var address = new Address(dto.getStreet(),
            dto.getCity(),
            dto.getZipCode(),
            dto.getCountry());
        customer.addAddress(address);
        return new AddressDto(address.getId(),
            address.getStreet(),
            address.getCity(),
            address.getZipCode(),
            address.getCountry());
    }
}

package pl.sda.spring.customers.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.spring.customers.dto.AddAddressDto;
import pl.sda.spring.customers.dto.AddAddressFromCoordinatesDto;
import pl.sda.spring.customers.dto.AddressDto;
import pl.sda.spring.customers.dto.CompanyDto;
import pl.sda.spring.customers.dto.CreateCompanyDto;
import pl.sda.spring.customers.dto.CreatePersonDto;
import pl.sda.spring.customers.dto.CustomerDto;
import pl.sda.spring.customers.dto.PersonDto;
import pl.sda.spring.customers.entity.Address;
import pl.sda.spring.customers.entity.Customer;
import pl.sda.spring.customers.entity.CustomerRepository;
import pl.sda.spring.customers.entity.Person;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final GeocodingService geocodingService;
    private final Mapper mapper;

    public CustomerService(CustomerRepository repository, GeocodingService geocodingService,
        Mapper mapper) {
        this.repository = repository;
        this.geocodingService = geocodingService;
        this.mapper = mapper;
    }

    public List<CustomerDto> listCustomers() {
        return repository.findAll()
            .stream()
            .map(mapper::mapToDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public PersonDto createCustomer(CreatePersonDto dto) {
        final var person = repository.findByPesel(dto.getPesel());
        if (person != null) {
            throw new IllegalArgumentException("Person with PESEL: " + dto.getPesel() + " already exists.");
        }
        final var newPerson = repository.save(new Person(dto.getFirstName(), dto.getLastName(), dto.getPesel()));
        return mapper.mapToDto(newPerson);
    }

    @Transactional
    public CompanyDto createCustomer(CreateCompanyDto dto) {
        // TODO - implement
        return null;
    }

    @Transactional
    public AddressDto addAddress(AddAddressDto dto) {
        final var customer = repository.getOne(dto.getCustomerId());
        final var address = new Address(dto.getStreet(),
            dto.getCity(),
            dto.getZipCode(),
            dto.getCountry());
        customer.addAddress(address);
        return mapper.mapToDto(address);
    }

    @Transactional
    public AddressDto addAddress(AddAddressFromCoordinatesDto dto) {
        final var customer = repository.getOne(dto.getCustomerId());
        final var address = geocodingService.reverse(dto.getLatitude(), dto.getLongitude());
        customer.addAddress(address);
        return mapper.mapToDto(address);
    }

    public CustomerDto getCustomer(UUID id) {
        final var customer = repository.getOne(id);
        return mapper.mapToDto(Hibernate.unproxy(customer, Customer.class));
    }
}

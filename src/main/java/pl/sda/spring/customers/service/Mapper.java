package pl.sda.spring.customers.service;

import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Component;
import pl.sda.spring.customers.dto.AddressDto;
import pl.sda.spring.customers.dto.CompanyDto;
import pl.sda.spring.customers.dto.CustomerDto;
import pl.sda.spring.customers.dto.PersonDto;
import pl.sda.spring.customers.entity.Address;
import pl.sda.spring.customers.entity.Company;
import pl.sda.spring.customers.entity.Customer;
import pl.sda.spring.customers.entity.Person;

@Component
final class Mapper {

    CustomerDto mapToDto(Customer customer) {
        if (customer instanceof Person) {
            return mapToDto((Person) customer);
        } else if (customer instanceof Company) {
            return mapToDto((Company) customer);
        }
        throw new IllegalArgumentException("Customer type not implemented: " + customer);
    }

    PersonDto mapToDto(Person newPerson) {
        return new PersonDto(newPerson.getId(),
            newPerson.getAddresses()
                .stream()
                .map(this::mapToDto)
                .collect(toList()),
            newPerson.getFirstName(),
            newPerson.getLastName(),
            newPerson.getPesel());
    }

    CompanyDto mapToDto(Company company) {
        return new CompanyDto(company.getId(),
            company.getAddresses()
                .stream()
                .map(this::mapToDto)
                .collect(toList()),
            company.getName(),
            company.getVatNumber());
    }

    AddressDto mapToDto(Address address) {
        return new AddressDto(address.getId(),
            address.getStreet(),
            address.getCity(),
            address.getZipCode(),
            address.getCountry());
    }
}

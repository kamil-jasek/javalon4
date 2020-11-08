package pl.sda.spring.customers.service;

import java.util.Collections;
import org.springframework.stereotype.Component;
import pl.sda.spring.customers.dto.AddressDto;
import pl.sda.spring.customers.dto.PersonDto;
import pl.sda.spring.customers.entity.Address;
import pl.sda.spring.customers.entity.Person;

@Component
final class Mapper {

    PersonDto mapToDto(Person newPerson) {
        return new PersonDto(newPerson.getId(),
            Collections.emptyList(),
            newPerson.getFirstName(),
            newPerson.getLastName(),
            newPerson.getPesel());
    }

    AddressDto mapToDto(Address address) {
        return new AddressDto(address.getId(),
            address.getStreet(),
            address.getCity(),
            address.getZipCode(),
            address.getCountry());
    }
}

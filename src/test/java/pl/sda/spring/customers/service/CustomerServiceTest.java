package pl.sda.spring.customers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.spring.customers.dto.AddAddressDto;
import pl.sda.spring.customers.dto.AddAddressFromCoordinatesDto;
import pl.sda.spring.customers.dto.AddressDto;
import pl.sda.spring.customers.dto.CreatePersonDto;
import pl.sda.spring.customers.dto.PersonDto;
import pl.sda.spring.customers.entity.Customer;
import pl.sda.spring.customers.entity.CustomerRepository;
import pl.sda.spring.customers.entity.Person;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    void testCreatePerson() {
        // given
        final var createDto = new CreatePersonDto("Jan", "Nowak", "8392929929");

        // when
        final var personDto = customerService.createCustomer(createDto);

        // then
        assertNotNull(personDto);
        assertNotNull(personDto.getId());
        assertEquals(Collections.emptyList(), personDto.getAddresses());
        assertEquals("Jan", personDto.getFirstName());
        assertEquals("Nowak", personDto.getLastName());
        assertEquals("8392929929", personDto.getPesel());
    }

    @Test
    @Transactional
    void testShouldFailWhenPeselExists() {
        // given
        final var createDto = new CreatePersonDto("Jan", "Nowak", "8392929929");
        saveAndClear(new Person("Jan", "Nowak", "8392929929"));

        // when
        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(createDto));
    }

    @Test
    @Transactional
    void testAddAddress() {
        // given
        final var person = new Person("Jan", "Nowak", "8392929929");
        saveAndClear(person);

        // when
        final var addressDto = customerService
            .addAddress(new AddAddressDto(person.getId(), "str", "city", "01-300", "PL"));

        // then
        assertNotNull(addressDto.getId());
        assertEquals("str", addressDto.getStreet());
        assertEquals("city", addressDto.getCity());
        assertEquals("01-300", addressDto.getZipCode());
        assertEquals("PL", addressDto.getCountry());
    }

    @Test
    @Transactional
    void testAddAddressFromCoordinates() {
        // given
        final var person = new Person("Jan", "Nowak", "8392929929");
        saveAndClear(person);

        // when
        final var address = customerService.addAddress(
            new AddAddressFromCoordinatesDto(person.getId(), 52.257502, 21.102816));

        // then
        assertNotNull(address);
        assertNotNull(address.getId());
        assertEquals("Dudziarska 4/2", address.getStreet());
        assertEquals("Warszawa", address.getCity());
        assertEquals("04-317", address.getZipCode());
        assertEquals("PL", address.getCountry());
    }

    private void saveAndClear(Customer... args) {
        for (Customer customer : args) {
            repository.saveAndFlush(customer);
        }
        em.clear();
    }
}

package pl.sda.spring.customers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.spring.customers.dto.CreatePersonDto;
import pl.sda.spring.customers.dto.PersonDto;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

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
        customerService.createCustomer(createDto);

        // when
        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(createDto));
    }
}

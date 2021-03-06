package pl.sda.spring.customers.entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.sda.spring.customers.entity.CustomerSpec.withQuery;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.spring.customers.entity.CustomerRepository.StreetByZipCodeCountry;
import pl.sda.spring.customers.entity.CustomerSpec.CompanyQuery;

@SpringBootTest
class CustomerTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void testCreatePerson() {
        // given:
        final var customer = new Person("Jan", "Nowak", "02939499393");
        final var address = new Address("Testowa", "Test", "02-304", "PL");
        customer.addAddress(address);

        // when:
        saveAndClear(customer);

        // then:
        final var readCustomer = repository.getOne(customer.getId());
        assertEquals(readCustomer, customer);
        assertEquals(address, readCustomer.getAddresses().get(0));
    }

    @Test
    @Transactional
    void testUpdateCompany() {
        // given:
        final var company = new Company("TEST S.A.", "PL3939939");
        saveAndClear(company);

        // when:
        company.updateName("TESTOWO S.A.");
        saveAndClear(company);

        // then:
        final var readCompany = repository.getOne(company.getId());
        assertEquals(readCompany, company);
    }

    @Test
    @Transactional
    void testFindCompanyByVat() {
        // given:
        saveAndClear(new Company("TEST1", "PL0303003"));
        saveAndClear(new Company("TEST2", "PL9292992"));

        // when:
        final var customer = repository.findByVatNumber("PL9292992");

        // then:
        assertTrue(customer.isPresent());
        assertEquals("PL9292992", customer.get().getVatNumber());
    }

    @Test
    @Transactional
    void testFindCustomersByCity() {
        // given
        final var customer1 = new Company("TEST S.A.", "PL9393939");
        customer1.addAddress(new Address("street", "Cracow", "01-600", "PL"));
        customer1.addAddress(new Address("street", "Warsaw", "01-230", "PL"));

        final var customer2 = new Company("TEST 2", "PL39933020");
        customer2.addAddress(new Address("str", "London", "03-122", "UK"));

        saveAndClear(customer1, customer2);

        // when
        final var customers = repository.findByAddressesCity("Warsaw");

        // then
        assertFalse(customers.isEmpty());
        assertTrue(customers.stream()
            .allMatch(c -> c.getAddresses().stream()
                .anyMatch(address -> address.getCity().equals("Warsaw"))));
    }

    @Test
    @Transactional
    void testFindPersonByLastnameFromCountry() {
        // given
        final var person1 = new Person("Jan", "Nowak", "0202030030");
        person1.addAddress(new Address("str1", "Warsaw", "03-230", "PL"));
        person1.addAddress(new Address("str2", "Cracow", "04-202", "PL"));
        final var person2 = new Person("Adam", "Jacek", "2883903003");
        person2.addAddress(new Address("str2", "Londo", "04-222", "UK"));

        saveAndClear(person1, person2);

        // when
        final var customers = repository.findByLastnameFromCountry("jacek", "uk");

        // then
        assertFalse(customers.isEmpty());
        assertTrue(customers.stream()
            .allMatch(person -> person.getLastName().equals("Jacek") && person.getAddresses().stream()
                .anyMatch(address -> address.getCountry().equals("UK"))));
    }

    @Test
    @Transactional
    void testFindAllCustomersFromZipCodeAndCountry() {
        // given - create two customers, add addresses
        final var zipCode = "01-300";
        final var country = "PL";
        final var company = new Company("TEST", "PL939399393");
        company.addAddress(new Address("str1", "cit1", zipCode, country));
        final var person = new Person("Jan", "Nowak", "PL93999333");
        person.addAddress(new Address("str2", "cit2", "01-240", "UK"));

        saveAndClear(company, person);

        // when
        final List<Customer> customers = repository.findByAllCustomersFromZipCodeAndCountry(zipCode, country);

        // then
        assertFalse(customers.isEmpty());
        assertTrue(customers.stream()
            .allMatch(customer -> customer.getAddresses().stream()
                .anyMatch(address -> address.getZipCode().equals(zipCode) && address.getCountry().equals(country))));
    }

    @Test
    @Transactional
    void testCountCustomersByCity() {
        // given
        final var customer1 = new Company("TEST S.A.", "PL9393939");
        customer1.addAddress(new Address("street", "Cracow", "01-600", "PL"));
        customer1.addAddress(new Address("street", "Manchester", "01-230", "UK"));

        final var customer2 = new Company("TEST 2", "PL39933020");
        customer2.addAddress(new Address("str", "London", "03-122", "UK"));

        final var customer3 = new Company("TEST 3", "PL39933021");
        customer2.addAddress(new Address("str", "London", "03-122", "UK"));

        saveAndClear(customer1, customer2, customer3);

        // when
        final var results = repository.countCustomersByCity("UK");

        // then
        assertFalse(results.isEmpty());
        assertArrayEquals(new Object[] { "London", BigInteger.valueOf(2) }, results.get(0));
        assertArrayEquals(new Object[] { "Manchester", BigInteger.valueOf(1) }, results.get(1));
    }

    @Test
    @Transactional
    void testCountByCity() {
        // given
        final var customer1 = new Company("TEST S.A.", "PL9393939");
        customer1.addAddress(new Address("street", "Cracow", "01-600", "PL"));
        customer1.addAddress(new Address("street", "Manchester", "01-230", "UK"));

        final var customer2 = new Company("TEST 2", "PL39933020");
        customer2.addAddress(new Address("str", "London", "03-122", "UK"));

        final var customer3 = new Company("TEST 3", "PL39933021");
        customer2.addAddress(new Address("str", "London", "03-122", "UK"));

        saveAndClear(customer1, customer2, customer3);

        // when
        final var results = repository.countByCity("UK");

        // then
        assertFalse(results.isEmpty());
        final var row1 = results.get(0);
        assertEquals("London", row1.getCity());
        assertEquals(2, row1.getCount());
        final var row2 = results.get(1);
        assertEquals("Manchester", row2.getCity());
        assertEquals(1, row2.getCount());
    }

    @Test
    @Transactional
    void testCountStreetsByZipCodeAndCountry() {
        // given - create customers and addresses
        final var customer1 = new Company("TEST S.A.", "PL9393939");
        customer1.addAddress(new Address("street", "Cracow", "03-230", "UK"));
        customer1.addAddress(new Address("street", "Manchester", "03-230", "UK"));
        customer1.addAddress(new Address("street", "Manchester", "01-030", "PL"));
        saveAndClear(customer1);

        // when
        final var results = repository.countStreetByZipCodeCountry();

        // then
        assertFalse(results.isEmpty());
        final var row1 = results.get(0);
        assertEquals(2, row1.getCount());
        assertEquals("03-230", row1.getZipCode());
        assertEquals("UK", row1.getCountry());

        final var row2 = results.get(1);
        assertEquals(1, row2.getCount());
        assertEquals("01-030", row2.getZipCode());
        assertEquals("PL", row2.getCountry());
    }

    @Test
    @Transactional
    void testUpdatePersonName() {
        // given
        final var person = new Person("Jan", "Kowalski", "93994302002");
        final var person2 = new Person("Adam", "Nowak", "8329292929");
        saveAndClear(person, person2);

        // when
        final var updated = repository.updatePersonName(person.getId(), "Jan", "Kowalsky");

        // then
        assertEquals(1, updated);
        final var readPerson = repository.findPersonById(person.getId());
        assertEquals("Kowalsky", readPerson.getLastName());
        assertEquals("Jan", readPerson.getFirstName());
    }

    @Test
    @Transactional
    void testUpdateAddress() {
        // given
        final var person = new Person("Jan", "Kowalski", "93994302002");
        person.addAddress(new Address("str1", "cit", "01-200", "PL"));
        saveAndClear(person);

        // when
        person.removeAddress(person.getAddresses().get(0));
        saveAndClear(person);

        final var address = new Address("str2", "cit", "01-200", "PL");
        person.addAddress(address);
        saveAndClear(person);

        // then
        final var readPerson = repository.findPersonById(person.getId());
        final var size = readPerson.getAddresses().size();
        assertEquals(1, size);
        assertEquals(address, readPerson.getAddresses().get(size - 1));
    }

    @Test
    @Transactional
    void testSpecification() {
        // given
        final var customer1 = new Company("TESY S.A.", "PL9393939");
        final var customer2 = new Company("TEST 2", "PL39933020");
        final var customer3 = new Company("TEST 3", "PL39933021");
        saveAndClear(customer1, customer2, customer3);

        // when: 1=1
        var results = repository.findAll(withQuery(new CompanyQuery(null, null)));
        // then:
        assertEquals(3, results.size());

        // when: 1=1 and name like :name
        results = repository.findAll(withQuery(new CompanyQuery("TEST", null)));
        // then
        assertEquals(2, results.size());

        // when 1=1 and vatNumber like :vatId
        results = repository.findAll(withQuery(new CompanyQuery(null, "PL399")));
        // then
        assertEquals(2, results.size());

        // when 1=1 and name like :name and vatNumber like :vatId
        results = repository.findAll(withQuery(new CompanyQuery("TES", "PL93")));
        // then
        assertEquals(1, results.size());
    }

    private void saveAndClear(Customer... args) {
        for (Customer customer : args) {
            repository.saveAndFlush(customer);
        }
        em.clear();
    }
}

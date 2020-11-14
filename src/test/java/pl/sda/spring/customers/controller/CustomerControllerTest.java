package pl.sda.spring.customers.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.spring.customers.entity.Customer;
import pl.sda.spring.customers.entity.Person;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    void testGetCustomers() throws Exception {
        // given
        final var person = new Person("Jan", "Nowak", "8392929929");
        saveAndClear(person);

        // when
        mockMvc.perform(get("/api/v1/customers"))
        // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", equalTo(1)))
            .andExpect(jsonPath("$[0].id", equalTo(person.getId().toString())))
            .andExpect(jsonPath("$[0].lastName", equalTo("Nowak")))
            .andExpect(jsonPath("$[0].firstName", equalTo("Jan")))
            .andExpect(jsonPath("$[0].pesel", equalTo("8392929929")))
            .andDo(print());
    }

    @Test
    @Transactional
    void testCreatePerson() throws Exception {
        // given
        final var jsonRequest = "{"
            + "\"type\": \"PERSON\","
            + "\"firstName\": \"Jan\","
            + "\"lastName\": \"Nowak\","
            + "\"pesel\": \"90283092020\""
            + "}";

        // when
        mockMvc.perform(post("/api/v1/customers")
            .content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON))
        // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.firstName", equalTo("Jan")))
            .andExpect(jsonPath("$.lastName", equalTo("Nowak")))
            .andExpect(jsonPath("$.pesel", equalTo("90283092020")))
            .andDo(print());
    }

    private void saveAndClear(Customer... args) {
        for (Customer customer : args) {
            em.persist(customer);
            em.flush();
        }
        em.clear();
    }
}

package pl.sda.spring.customers.controller;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.spring.customers.dto.AddAddressDto;
import pl.sda.spring.customers.dto.AddressDto;
import pl.sda.spring.customers.dto.CreateCompanyDto;
import pl.sda.spring.customers.dto.CreateCustomerDto;
import pl.sda.spring.customers.dto.CreatePersonDto;
import pl.sda.spring.customers.dto.CustomerDto;
import pl.sda.spring.customers.dto.PatchCustomerNameDto;
import pl.sda.spring.customers.dto.PatchPersonPeselDto;
import pl.sda.spring.customers.dto.UpdateCustomerDto;
import pl.sda.spring.customers.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
@PreAuthorize("hasRole('CUSTOMERS_READ')")
class CustomerController {

    private final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET -> /api/v1/customers
    @GetMapping
    List<CustomerDto> getCustomers() {
        return customerService.listCustomers();
    }

    // GET -> /api/v1/customers/75977120-0c75-469c-83e8-d21e5f895167
    @GetMapping("/{id}")
    ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(customerService.getCustomer(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST -> /api/v1/customers/75977120-0c75-469c-83e8-d21e5f895167/addresses
    @PreAuthorize("hasRole('CUSTOMERS_WRITE')")
    @PostMapping("/{id}/addresses")
    ResponseEntity<AddressDto> addAddress(@PathVariable UUID customerId, @RequestBody AddAddressDto dto) {
        // TODO
        return null;
    }

    // POST -> /api/v1/customers
    @PreAuthorize("hasRole('CUSTOMERS_WRITE')")
    @PostMapping
    ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerDto dto) {
        if (dto instanceof CreateCompanyDto) {
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.createCustomer((CreateCompanyDto) dto));
        } else if (dto instanceof CreatePersonDto) {
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.createCustomer((CreatePersonDto) dto));
        }
        throw new IllegalArgumentException("Invalid customer type: " + dto);
    }

    // PUT -> /api/v1/customers/9235ba6f-10b8-4a5e-8f81-f47f3722cbdf
//    @PutMapping("/{id}")
//    CustomerDto updateCustomer(@RequestBody UpdateCustomerDto dto) {
//        return null;
//    }

    // PATCH -> /api/v1/customers/aec14bde-e281-43c3-9e8f-8aafa19ca818/name
    @PreAuthorize("hasRole('CUSTOMERS_WRITE')")
    @PatchMapping("/{id}/name")
    CustomerDto patchName(@RequestBody PatchCustomerNameDto dto) {
        // TODO
        return null;
    }

    // PATCH -> /api/v1/customers/aec14bde-e281-43c3-9e8f-8aafa19ca818/name
    @PreAuthorize("hasRole('CUSTOMERS_WRITE')")
    @PatchMapping("/{id}/pesel")
    CustomerDto patchPesel(@RequestBody PatchPersonPeselDto dto) {
        // TODO
        return null;
    }

    // DELETE -> /api/v1/customers/78aa2e4b-4ed0-4041-b99a-97464e40eb4b
    @PreAuthorize("hasRole('CUSTOMERS_WRITE')")
    @DeleteMapping("/{id}")
    CustomerDto deleteCustomer(@PathVariable UUID id) {
        // TODO
        return null;
    }
}

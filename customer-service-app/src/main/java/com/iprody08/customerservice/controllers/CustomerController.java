package com.iprody08.customerservice.controllers;

import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.errors.NotFoundException;
import com.iprody08.customerservice.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Add a new customer", description = "Added customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        log.info("Add customer success");
        return customerService.addCustomer(customerDto);
    }

    @Operation(summary = "Get customer by ID", description = "Returns the customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CustomerDto> getCustomerById(@PathVariable("id") Long id) {
        return Optional.ofNullable(customerService
                .findCustomerById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Customer not found by id: %s", id))));
    }

    @Operation(
            summary = "Get customer by telegram id",
            description = "Returns the customer by telegram id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/telegram/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomerByTelegramId(@PathVariable String id) {
        return customerService
                .findCustomerByTelegramId(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Customer not found by telegram id: %s", id)));
    }

    @Operation(summary = "Get all customers", description = "Returns all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers found")
    })
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(Pageable pageable) {
        List<CustomerDto> customers = customerService.findAllCustomers(pageable);
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Update customer by ID", description = "Returns the updated customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id,
                                                      @Valid @RequestBody CustomerDto customerDto) {
        customerDto.setId(id);
        CustomerDto customer = customerService.updateCustomer(customerDto);
        return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Delete customer by ID", description = "Deletes the customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
    }
}

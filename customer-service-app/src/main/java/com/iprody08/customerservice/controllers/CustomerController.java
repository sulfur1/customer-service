package com.iprody08.customerservice.controllers;

import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.errors.NotFoundException;
import com.iprody08.customerservice.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Add a new customer", description = "Returns the added customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created")
    })
    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto customer = customerService.addCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @Operation(summary = "Get customer by ID", description = "Returns the customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomerById(@PathVariable long id) {
        return customerService
                .findCustomerById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Customer not found by id: %s", id)));
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
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable long id,
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

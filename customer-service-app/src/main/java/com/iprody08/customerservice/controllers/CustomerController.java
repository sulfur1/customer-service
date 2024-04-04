package com.iprody08.customerservice.controllers;

import com.iprody08.customerservice.dto.CustomerDTO;
import com.iprody08.customerservice.dto.CustomerListParams;
import com.iprody08.customerservice.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.addCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable long id) {
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(CustomerListParams customerListParams) {
        Page<CustomerDTO> customers = customerService.findAllCustomers(customerListParams);
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable long id,
                                                      @Valid @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(id);
        CustomerDTO customer = customerService.updateCustomer(customerDTO);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
    }
}

package com.myclub.computerclubmanagement.controller;


import com.myclub.computerclubmanagement.dto.CustomerRequest;
import com.myclub.computerclubmanagement.dto.CustomerResponse;
import com.myclub.computerclubmanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestBody CustomerRequest username) {

        customerService.createCustomer(username);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAll() {
        return customerService.findAll();
    }
}

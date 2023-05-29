package com.myclub.computerclubmanagement.service;

import com.myclub.computerclubmanagement.dto.CustomerRequest;
import com.myclub.computerclubmanagement.dto.CustomerResponse;
import com.myclub.computerclubmanagement.model.customer.Customer;
import com.myclub.computerclubmanagement.model.customer.Status;
import com.myclub.computerclubmanagement.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void createCustomer(CustomerRequest request) {
        boolean isPresent = customerRepository.findAll().stream()
                .anyMatch(customer -> request.getUsername().equals(customer.getUsername()));
        if (isPresent) {
            throw new IllegalStateException("Username " + request.getUsername() + " already exist!");
          }
        Customer customer = mapDtoToCustomer(request);
        customerRepository.insert(customer);
        log.info("Customer: " + customer.getUsername() + " is created");

    }


    private Customer mapDtoToCustomer(CustomerRequest request) {
        return Customer.builder()
                .username(request.getUsername())
                .status(Status.SILVER)
                .totalSpendingMoney(0)
                .totalSpendingTime(0)
                .build();
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream().map(this::mapCustomerToDto).toList();
    }

    private CustomerResponse mapCustomerToDto(Customer customer) {
        return CustomerResponse.builder()
                .username(customer.getUsername())
                .status(customer.getStatus().name())
                .totalMoneySpending(customer.getTotalSpendingMoney())
                .totalTimeSpending(customer.getTotalSpendingTime())
                .build();
    }

    public CustomerResponse findByUsername(String username) {
        Optional<Customer> optionalCustomer = customerRepository.findAll().stream()
                .filter(el -> el.getUsername().equals(username))
                .findFirst();

        if (optionalCustomer.isPresent()) {
            return mapCustomerToDto(optionalCustomer.get());
        }

        throw new IllegalStateException("Customer: " + username + " is not found");
    }

    @Transactional
    public void updateCustomerStat(CustomerResponse customerResponse, int duration, int costPerHour) {
        Customer customer = customerRepository.findAll().stream()
                .filter(el -> el.getUsername().equals(customerResponse.getUsername()))
                .findFirst().orElseThrow();

        customer.setTotalSpendingTime(customer.getTotalSpendingTime() + duration);
        customer.setTotalSpendingMoney(customer.getTotalSpendingMoney()
            + (duration * costPerHour)
        );

        customerRepository.save(customer);
    }
}

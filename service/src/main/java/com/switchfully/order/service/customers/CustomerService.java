package com.switchfully.order.service.customers;

import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerRepository;
import com.switchfully.order.infrastructure.exceptions.EntityNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;

    @Inject
    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    public Customer createCustomer(Customer customer) {
        if (!customerValidator.isValidForCreation(customer)) {
            customerValidator.throwInvalidStateException(customer, "creation");
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(UUID id) {
        var customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new EntityNotFoundException("lookup", Customer.class, id);
        }
        return customer.get();
    }

}

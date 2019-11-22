package com.switchfully.order.domain.customers;

import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
public interface CustomerRepository extends CrudRepository<Customer, UUID>{
    List<Customer> findAll();
}

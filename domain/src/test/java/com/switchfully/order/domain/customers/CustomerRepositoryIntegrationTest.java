package com.switchfully.order.domain.customers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.util.List;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;

@SpringBootTest
@AutoConfigureTestDatabase
class CustomerRepositoryIntegrationTest {

    @Inject
    private CustomerRepository customerRepository;

    @BeforeEach
    public void clearDB() {
        customerRepository.deleteAll();
    }

    @Test
    void saveCustomer() {
        Customer customerToSave = aCustomer().build();

        Customer savedCustomer = customerRepository.save(customerToSave);
        Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).get();

        Assertions.assertThat(savedCustomer.getId()).isNotNull();
        Assertions.assertThat(foundCustomer.getId()).isEqualTo(savedCustomer.getId());
    }

    @Test
    void getCustomerById() {
        Customer savedCustomer = customerRepository.save(aCustomer().build());

        Customer actualCustomer = customerRepository.findById(savedCustomer.getId()).get();

        Assertions.assertThat(actualCustomer.getId()).isEqualTo(savedCustomer.getId());
    }

    @Test
    void getAll() {
        Customer customerOne = customerRepository.save(aCustomer().build());
        Customer customerTwo = customerRepository.save(aCustomer().build());

        List<Customer> allCustomers = customerRepository.findAll();

        Assertions.assertThat(allCustomers)
                .containsExactlyInAnyOrder(customerOne, customerTwo);
    }

}
package com.switchfully.order.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BaseEntityTest {

    @Test
    void createCustomer_whenCreatingCustomer_thenCreateCustomerWithId() {
        BaseEntity aCustomer = aCustomer().build();
        Assertions.assertThat(aCustomer.getId())
                .isNotNull()
                .isNotEqualTo("");
    }

    @Test
    void generateId_givenCustomerWithId_whenGeneratingId_thenThrowException() {
        UUID id = UUID.randomUUID();
        BaseEntity customer = aCustomer().withId(id).build();

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(customer::generateId)
                .withMessage("Generating an ID for a customer that already has an ID (" + customer.getId() + ") is not allowed.");
    }

}

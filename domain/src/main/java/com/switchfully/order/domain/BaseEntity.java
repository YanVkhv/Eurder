package com.switchfully.order.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "ID", length = 16, unique = true, nullable = false)
    private UUID id;

    public BaseEntity() {}

    protected BaseEntity(UUID id) {
        this.id = generateId();
    }

    public UUID generateId() throws IllegalStateException {
        if (id != null) {
            throw new IllegalStateException("Generating an ID for a customer that already has " +
                    "an ID (" + id + ") is not allowed.");
        }
        return UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

}

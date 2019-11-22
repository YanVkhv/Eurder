package com.switchfully.order.domain.items;

import org.springframework.data.repository.CrudRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
public interface ItemRepository extends CrudRepository<Item, UUID> {
    List<Item> findAll();
}

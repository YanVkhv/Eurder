package com.switchfully.order.domain.items;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;

import static com.switchfully.order.domain.items.ItemTestBuilder.anItem;

@SpringBootTest
@AutoConfigureTestDatabase
class ItemRepositoryIntegrationTest {

    @Inject
    private ItemRepository itemRepository;

    @BeforeEach
    public void clearDB() {
        itemRepository.deleteAll();
    }

    @Test
    void createItem() {
        Item itemToCreate = anItem().build();

        Item createdItem = itemRepository.save(itemToCreate);
        Item foundItem = itemRepository.findById(createdItem.getId()).get();

        Assertions.assertThat(createdItem.getId()).isNotNull();
        Assertions.assertThat(foundItem.getId()).isEqualTo(createdItem.getId());
    }

    @Test
    void getItemById() {
        Item savedItem = itemRepository.save(anItem().build());

        Item actualItem = itemRepository.findById(savedItem.getId()).get();

        Assertions.assertThat(actualItem.getId()).isEqualTo(savedItem.getId());
    }

    @Test
    void updateItem() {
        Item savedItem = itemRepository.save(anItem().build());
        savedItem.setName("An updated version");
        savedItem.setDescription("An updated version");

        Item updatedItem = itemRepository.save(savedItem);

        Assertions.assertThat(savedItem.getId()).isEqualTo(updatedItem.getId());
        Assertions.assertThat(savedItem.getName()).isEqualTo(updatedItem.getName());
    }

}
package com.switchfully.order.service.items;

import com.switchfully.order.domain.items.Item;
import com.switchfully.order.domain.items.ItemRepository;
import com.switchfully.order.infrastructure.exceptions.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @Inject
    public ItemService(ItemRepository itemRepository, ItemValidator itemValidator) {
        this.itemRepository = itemRepository;
        this.itemValidator = itemValidator;
    }

    public Item createItem(Item item) {
        if (!itemValidator.isValidForCreation(item)) {
            itemValidator.throwInvalidStateException(item, "creation");
        }
        return itemRepository.save(item);
    }

    public Item updateItem(String id, Item item) {
        if (!itemValidator.isValidForUpdating(item)) {
            itemValidator.throwInvalidStateException(item, "Valid update received, finding item to be updated in database...");
        }
        getItem(UUID.fromString(id)).setName(item.getName());
        getItem(UUID.fromString(id)).setDescription(item.getDescription());
        getItem(UUID.fromString(id)).setPrice(item.getPrice());
        getItem(UUID.fromString(id)).setAmountOfStock(item.getAmountOfStock());
        return getItem(UUID.fromString(id));
    }

    public Item getItem(UUID itemId) {
        var item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new EntityNotFoundException("lookup", Item.class, itemId);
        }
        return item.get();
    }

//    public void decrementStockForItem(UUID itemId, int amountToDecrement) {
//        Item item = itemRepository.get(itemId);
//        item.decrementStock(amountToDecrement);
//        itemRepository.update(item);
//    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}

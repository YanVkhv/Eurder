package com.switchfully.order.domain.items;

import com.switchfully.order.domain.BaseEntity;
import com.switchfully.order.domain.items.prices.Price;
import com.switchfully.order.infrastructure.builder.Builder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ITEM")
public class Item extends BaseEntity {

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "PRICE"))
    private Price price;

    @Column(name = "IN_STOCK")
    private int amountOfStock;

    public Item(){}

    public Item(ItemBuilder itemBuilder) {
        super(itemBuilder.id);
        name = itemBuilder.name;
        description = itemBuilder.description;
        price = itemBuilder.price;
        amountOfStock = itemBuilder.amountOfStock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setAmountOfStock(int amountOfStock) {
        this.amountOfStock = amountOfStock;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public int getAmountOfStock() {
        return amountOfStock;
    }

    public StockUrgency getStockUrgency() {
        if(amountOfStock < 5) { return StockUrgency.STOCK_LOW; }
        if (amountOfStock < 10) { return StockUrgency.STOCK_MEDIUM; }
        return StockUrgency.STOCK_HIGH;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amountOfStock=" + amountOfStock +
                '}';
    }

    public void decrementStock(int amountToDecrement) {
        if(amountToDecrement > amountOfStock) {
            throw new IllegalArgumentException("Decrementing the stock amount of an item " + getId().toString()
                    + " below 0 is not allowed");
        }
        amountOfStock -= amountToDecrement;
    }

    public static class ItemBuilder extends Builder<Item> {

        private UUID id;
        private String name;
        private String description;
        private Price price;
        private int amountOfStock;

        public static ItemBuilder item() {
            return new ItemBuilder();
        }

        @Override
        public Item build() {
            return new Item(this);
        }

        public ItemBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public ItemBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ItemBuilder withPrice(Price price) {
            this.price = price;
            return this;
        }

        public ItemBuilder withAmountOfStock(int amountOfStock) {
            this.amountOfStock = amountOfStock;
            return this;
        }
    }

    public enum StockUrgency {
        STOCK_LOW,
        STOCK_MEDIUM,
        STOCK_HIGH;
    }
}

package com.cvs.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CoffeeMenu {
    private int coffeeId;
    private String name;
    private BigDecimal price;
    private String description;
    private boolean available;
    private LocalDateTime createdAt;

    public CoffeeMenu() {}

    public CoffeeMenu(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = true;
    }

    public CoffeeMenu(int coffeeId, String name, BigDecimal price, String description, boolean available) {
        this.coffeeId = coffeeId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
    }

    // Getters and Setters
    public int getCoffeeId() { return coffeeId; }
    public void setCoffeeId(int coffeeId) { this.coffeeId = coffeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return name + " - $" + price + (available ? "" : " (Unavailable)");
    }
}
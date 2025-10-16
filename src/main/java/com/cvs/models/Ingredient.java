package com.cvs.models;

import java.time.LocalDateTime;

public class Ingredient {
    private int ingredientId;
    private String name;
    private int quantity;
    private String unit;
    private int minThreshold;
    private LocalDateTime updatedAt;

    public Ingredient() {}

    public Ingredient(String name, int quantity, String unit, int minThreshold) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.minThreshold = minThreshold;
    }

    // Getters and Setters
    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getMinThreshold() { return minThreshold; }
    public void setMinThreshold(int minThreshold) { this.minThreshold = minThreshold; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isLowStock() {
        return quantity <= minThreshold;
    }

    @Override
    public String toString() {
        return name + ": " + quantity + " " + unit + (isLowStock() ? " (LOW STOCK)" : "");
    }
}
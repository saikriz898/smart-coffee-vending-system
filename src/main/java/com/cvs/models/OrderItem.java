package com.cvs.models;

import java.math.BigDecimal;

public class OrderItem {
    public enum SugarLevel { NO_SUGAR, LOW, MEDIUM, HIGH }
    public enum MilkLevel { NO_MILK, LOW, MEDIUM, HIGH }
    public enum Size { SMALL, MEDIUM, LARGE }

    private int orderItemId;
    private int orderId;
    private int coffeeId;
    private int quantity;
    private SugarLevel sugarLevel;
    private MilkLevel milkLevel;
    private Size size;
    private BigDecimal itemPrice;
    private String coffeeName; // For display purposes

    public OrderItem() {}

    public OrderItem(int orderId, int coffeeId, int quantity, SugarLevel sugarLevel, 
                     MilkLevel milkLevel, Size size, BigDecimal itemPrice) {
        this.orderId = orderId;
        this.coffeeId = coffeeId;
        this.quantity = quantity;
        this.sugarLevel = sugarLevel;
        this.milkLevel = milkLevel;
        this.size = size;
        this.itemPrice = itemPrice;
    }

    // Getters and Setters
    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getCoffeeId() { return coffeeId; }
    public void setCoffeeId(int coffeeId) { this.coffeeId = coffeeId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public SugarLevel getSugarLevel() { return sugarLevel; }
    public void setSugarLevel(SugarLevel sugarLevel) { this.sugarLevel = sugarLevel; }

    public MilkLevel getMilkLevel() { return milkLevel; }
    public void setMilkLevel(MilkLevel milkLevel) { this.milkLevel = milkLevel; }

    public Size getSize() { return size; }
    public void setSize(Size size) { this.size = size; }

    public BigDecimal getItemPrice() { return itemPrice; }
    public void setItemPrice(BigDecimal itemPrice) { this.itemPrice = itemPrice; }

    public String getCoffeeName() { return coffeeName; }
    public void setCoffeeName(String coffeeName) { this.coffeeName = coffeeName; }

    @Override
    public String toString() {
        return quantity + "x " + coffeeName + " (" + size + ", Sugar: " + sugarLevel + 
               ", Milk: " + milkLevel + ") - $" + itemPrice;
    }
}
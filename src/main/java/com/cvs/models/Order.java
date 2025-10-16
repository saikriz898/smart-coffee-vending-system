package com.cvs.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public enum PaymentStatus { PENDING, COMPLETED, FAILED, REFUNDED }
    public enum OrderStatus { PLACED, PREPARING, READY, DELIVERED, CANCELLED }

    private int orderId;
    private int userId;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private LocalDateTime orderTime;
    private List<OrderItem> orderItems;

    public Order() {}

    public Order(int userId, BigDecimal totalAmount) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be positive");
        }
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.paymentStatus = PaymentStatus.PENDING;
        this.orderStatus = OrderStatus.PLACED;
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }

    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    @Override
    public String toString() {
        return "Order{orderId=" + orderId + ", userId=" + userId + ", totalAmount=" + totalAmount + 
               ", status=" + orderStatus + "}";
    }
}
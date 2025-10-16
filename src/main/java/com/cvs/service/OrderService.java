package com.cvs.service;

import com.cvs.dao.CoffeeMenuDAO;
import com.cvs.dao.OrderDAO;
import com.cvs.dao.OrderItemDAO;
import com.cvs.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final CoffeeMenuDAO coffeeMenuDAO;
    private final UserService userService;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.orderItemDAO = new OrderItemDAO();
        this.coffeeMenuDAO = new CoffeeMenuDAO();
        this.userService = new UserService();
    }

    public BigDecimal calculateItemPrice(int coffeeId, OrderItem.Size size, OrderItem.SugarLevel sugar, OrderItem.MilkLevel milk) {
        CoffeeMenu coffee = coffeeMenuDAO.getCoffeeById(coffeeId);
        if (coffee == null) return BigDecimal.ZERO;

        BigDecimal basePrice = coffee.getPrice();
        BigDecimal sizeMultiplier = getSizeMultiplier(size);
        BigDecimal customizationFee = getCustomizationFee(sugar, milk);

        return basePrice.multiply(sizeMultiplier).add(customizationFee);
    }

    private BigDecimal getSizeMultiplier(OrderItem.Size size) {
        switch (size) {
            case SMALL: return new BigDecimal("0.8");
            case MEDIUM: return BigDecimal.ONE;
            case LARGE: return new BigDecimal("1.3");
            default: return BigDecimal.ONE;
        }
    }

    private BigDecimal getCustomizationFee(OrderItem.SugarLevel sugar, OrderItem.MilkLevel milk) {
        BigDecimal fee = BigDecimal.ZERO;
        
        if (sugar == OrderItem.SugarLevel.HIGH) {
            fee = fee.add(new BigDecimal("0.25"));
        }
        if (milk == OrderItem.MilkLevel.HIGH) {
            fee = fee.add(new BigDecimal("0.50"));
        }
        
        return fee;
    }

    public Order createOrder(int userId, List<OrderItem> items) {
        try {
            if (items == null || items.isEmpty()) {
                logger.warn("Cannot create order with empty items");
                return null;
            }

            // Calculate total amount
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItem item : items) {
                BigDecimal itemPrice = calculateItemPrice(item.getCoffeeId(), item.getSize(), 
                                                        item.getSugarLevel(), item.getMilkLevel());
                item.setItemPrice(itemPrice.multiply(new BigDecimal(item.getQuantity())));
                totalAmount = totalAmount.add(item.getItemPrice());
            }

            // Check user balance
            User user = userService.getUserById(userId);
            if (user == null || user.getBalance().compareTo(totalAmount) < 0) {
                logger.warn("Insufficient balance for order: userId={}, required={}, available={}", 
                           userId, totalAmount, user != null ? user.getBalance() : "N/A");
                return null;
            }

            // Create order
            Order order = new Order(userId, totalAmount);
            order.setOrderItems(items);

            if (orderDAO.createOrder(order)) {
                logger.info("Order created successfully: orderId={}, amount={}", order.getOrderId(), totalAmount);
                return order;
            }

            return null;
        } catch (Exception e) {
            logger.error("Error creating order: {}", e.getMessage());
            return null;
        }
    }

    public boolean processPayment(int orderId, Payment.PaymentType paymentType) {
        try {
            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                logger.warn("Order not found for payment: orderId={}", orderId);
                return false;
            }

            // For wallet payment, deduct from user balance
            if (paymentType == Payment.PaymentType.WALLET) {
                boolean balanceDeducted = userService.deductBalance(order.getUserId(), order.getTotalAmount());
                if (!balanceDeducted) {
                    logger.warn("Failed to deduct balance for order: orderId={}", orderId);
                    return false;
                }
            }

            // Update order payment status
            boolean paymentUpdated = orderDAO.updatePaymentStatus(orderId, Order.PaymentStatus.COMPLETED);
            if (paymentUpdated) {
                orderDAO.updateOrderStatus(orderId, Order.OrderStatus.PREPARING);
                logger.info("Payment processed successfully: orderId={}, type={}", orderId, paymentType);
                return true;
            }

            return false;
        } catch (Exception e) {
            logger.error("Error processing payment: {}", e.getMessage());
            return false;
        }
    }

    public List<Order> getUserOrders(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public boolean updateOrderStatus(int orderId, Order.OrderStatus status) {
        return orderDAO.updateOrderStatus(orderId, status);
    }
}
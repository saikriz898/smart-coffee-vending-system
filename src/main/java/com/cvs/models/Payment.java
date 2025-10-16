package com.cvs.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    public enum PaymentType { CASH, CARD, WALLET, UPI }
    public enum PaymentStatus { SUCCESS, FAILED, PENDING }

    private int paymentId;
    private int orderId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDateTime paymentTime;

    public Payment() {}

    public Payment(int orderId, BigDecimal amount, PaymentType paymentType) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    // Getters and Setters
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }

    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }

    @Override
    public String toString() {
        return "Payment{paymentId=" + paymentId + ", orderId=" + orderId + ", amount=" + amount + 
               ", type=" + paymentType + ", status=" + paymentStatus + "}";
    }
}
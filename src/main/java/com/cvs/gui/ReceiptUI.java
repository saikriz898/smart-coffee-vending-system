package com.cvs.gui;

import com.cvs.models.Order;
import com.cvs.models.OrderItem;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ReceiptUI extends JDialog {
    private static final Color PRIMARY_COLOR = new Color(139, 69, 19);
    private static final Color SECONDARY_COLOR = new Color(210, 180, 140);

    public ReceiptUI(JFrame parent, Order order) {
        super(parent, "Receipt", true);
        initializeUI(order);
    }

    private void initializeUI(Order order) {
        setSize(400, 500);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Receipt content
        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setText(generateReceiptText(order));

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Receipt"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton printBtn = new JButton("Print");
        printBtn.setBackground(PRIMARY_COLOR);
        printBtn.setForeground(Color.WHITE);
        printBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Print functionality would be implemented here", "Print", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(SECONDARY_COLOR);
        closeBtn.addActionListener(e -> dispose());

        buttonPanel.add(printBtn);
        buttonPanel.add(closeBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private String generateReceiptText(Order order) {
        StringBuilder receipt = new StringBuilder();
        
        // Header
        receipt.append("        ☕ COFFEE VENDING SYSTEM\n");
        receipt.append("        ======================\n\n");
        
        // Order details
        receipt.append(String.format("Order ID: #%d\n", order.getOrderId()));
        receipt.append(String.format("Date: %s\n", 
            order.getOrderTime() != null ? 
                order.getOrderTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : 
                "N/A"));
        receipt.append(String.format("Status: %s\n", order.getOrderStatus()));
        receipt.append("\n");
        
        // Items
        receipt.append("ITEMS:\n");
        receipt.append("-".repeat(40)).append("\n");
        
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                receipt.append(String.format("%-20s x%d\n", item.getCoffeeName(), item.getQuantity()));
                receipt.append(String.format("  Size: %-10s Sugar: %s\n", item.getSize(), item.getSugarLevel()));
                receipt.append(String.format("  Milk: %-10s Price: $%.2f\n", item.getMilkLevel(), item.getItemPrice()));
                receipt.append("\n");
            }
        }
        
        receipt.append("-".repeat(40)).append("\n");
        receipt.append(String.format("TOTAL: $%.2f\n", order.getTotalAmount()));
        receipt.append(String.format("Payment: %s\n", order.getPaymentStatus()));
        receipt.append("\n");
        
        // Footer
        receipt.append("Thank you for your order!\n");
        receipt.append("Enjoy your coffee! ☕\n\n");
        receipt.append("        Have a great day!\n");
        
        return receipt.toString();
    }
}
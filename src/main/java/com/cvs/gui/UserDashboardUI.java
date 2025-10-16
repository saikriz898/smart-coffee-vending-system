package com.cvs.gui;

import com.cvs.dao.CoffeeMenuDAO;
import com.cvs.models.*;
import com.cvs.service.OrderService;
import com.cvs.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserDashboardUI extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(139, 69, 19);
    private static final Color SECONDARY_COLOR = new Color(210, 180, 140);
    
    private final User currentUser;
    private final UserService userService;
    private final OrderService orderService;
    private final CoffeeMenuDAO coffeeMenuDAO;
    
    private JLabel balanceLabel;
    private JTable menuTable;
    private List<OrderItem> cart;
    private JLabel cartTotalLabel;
    private JTextArea cartArea;

    public UserDashboardUI(User user) {
        this.currentUser = user;
        this.userService = new UserService();
        this.orderService = new OrderService();
        this.coffeeMenuDAO = new CoffeeMenuDAO();
        this.cart = new ArrayList<>();
        initializeUI();
        loadMenuData();
        updateBalanceDisplay();
    }

    private void initializeUI() {
        setTitle("☕ Coffee Vending System - User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);

        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Center - Menu and Cart
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        
        // Footer
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);

        balanceLabel = new JLabel("Balance: $0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setForeground(Color.WHITE);

        JButton addBalanceBtn = new JButton("Add Balance");
        addBalanceBtn.addActionListener(e -> showAddBalanceDialog());

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI().setVisible(true);
        });

        JPanel rightPanel = new JPanel(new FlowLayout());
        rightPanel.setBackground(PRIMARY_COLOR);
        rightPanel.add(balanceLabel);
        rightPanel.add(addBalanceBtn);
        rightPanel.add(logoutBtn);

        panel.add(welcomeLabel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(SECONDARY_COLOR);

        // Menu Panel
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Coffee Menu"));
        
        String[] columns = {"Name", "Price", "Description"};
        DefaultTableModel menuModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        menuTable = new JTable(menuModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane menuScroll = new JScrollPane(menuTable);
        menuPanel.add(menuScroll, BorderLayout.CENTER);
        
        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.addActionListener(e -> showAddToCartDialog());
        menuPanel.add(addToCartBtn, BorderLayout.SOUTH);

        // Cart Panel
        JPanel cartPanel = createCartPanel();

        panel.add(menuPanel);
        panel.add(cartPanel);

        return panel;
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));

        cartArea = new JTextArea(15, 30);
        cartArea.setEditable(false);
        cartArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane cartScroll = new JScrollPane(cartArea);
        panel.add(cartScroll, BorderLayout.CENTER);

        JPanel cartButtonPanel = new JPanel(new FlowLayout());
        cartTotalLabel = new JLabel("Total: $0.00");
        cartTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton clearCartBtn = new JButton("Clear Cart");
        clearCartBtn.addActionListener(e -> {
            cart.clear();
            updateCartDisplay();
        });
        
        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(e -> processCheckout());

        cartButtonPanel.add(cartTotalLabel);
        cartButtonPanel.add(clearCartBtn);
        cartButtonPanel.add(checkoutBtn);
        panel.add(cartButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(SECONDARY_COLOR);
        
        JButton ordersBtn = new JButton("My Orders");
        ordersBtn.addActionListener(e -> showOrderHistory());
        panel.add(ordersBtn);

        return panel;
    }

    private void loadMenuData() {
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
        model.setRowCount(0);
        
        List<CoffeeMenu> menuItems = coffeeMenuDAO.getAvailableCoffeeItems();
        for (CoffeeMenu item : menuItems) {
            model.addRow(new Object[]{
                item.getName(),
                "$" + item.getPrice(),
                item.getDescription()
            });
        }
    }

    private void updateBalanceDisplay() {
        User updatedUser = userService.getUserById(currentUser.getUserId());
        if (updatedUser != null) {
            currentUser.setBalance(updatedUser.getBalance());
            balanceLabel.setText("Balance: $" + currentUser.getBalance());
        }
    }

    private void showAddBalanceDialog() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to add:", "Add Balance", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.trim().isEmpty()) {
            try {
                BigDecimal amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) > 0) {
                    if (userService.addBalance(currentUser.getUserId(), amount)) {
                        updateBalanceDisplay();
                        JOptionPane.showMessageDialog(this, "Balance added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add balance", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAddToCartDialog() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a coffee item", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<CoffeeMenu> menuItems = coffeeMenuDAO.getAvailableCoffeeItems();
        CoffeeMenu selectedCoffee = menuItems.get(selectedRow);

        // Create dialog for customization
        JDialog dialog = new JDialog(this, "Customize Order", true);
        dialog.setSize(300, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Size
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Size:"), gbc);
        JComboBox<OrderItem.Size> sizeCombo = new JComboBox<>(OrderItem.Size.values());
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(sizeCombo, gbc);

        // Sugar
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Sugar:"), gbc);
        JComboBox<OrderItem.SugarLevel> sugarCombo = new JComboBox<>(OrderItem.SugarLevel.values());
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(sugarCombo, gbc);

        // Milk
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Milk:"), gbc);
        JComboBox<OrderItem.MilkLevel> milkCombo = new JComboBox<>(OrderItem.MilkLevel.values());
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(milkCombo, gbc);

        // Quantity
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Quantity:"), gbc);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(quantitySpinner, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add to Cart");
        addBtn.addActionListener(e -> {
            OrderItem item = new OrderItem();
            item.setCoffeeId(selectedCoffee.getCoffeeId());
            item.setCoffeeName(selectedCoffee.getName());
            item.setSize((OrderItem.Size) sizeCombo.getSelectedItem());
            item.setSugarLevel((OrderItem.SugarLevel) sugarCombo.getSelectedItem());
            item.setMilkLevel((OrderItem.MilkLevel) milkCombo.getSelectedItem());
            item.setQuantity((Integer) quantitySpinner.getValue());
            
            BigDecimal itemPrice = orderService.calculateItemPrice(
                item.getCoffeeId(), item.getSize(), item.getSugarLevel(), item.getMilkLevel());
            item.setItemPrice(itemPrice.multiply(new BigDecimal(item.getQuantity())));
            
            cart.add(item);
            updateCartDisplay();
            dialog.dispose();
        });
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void updateCartDisplay() {
        StringBuilder sb = new StringBuilder();
        BigDecimal total = BigDecimal.ZERO;
        
        for (int i = 0; i < cart.size(); i++) {
            OrderItem item = cart.get(i);
            sb.append(String.format("%d. %s\n", i + 1, item.toString()));
            total = total.add(item.getItemPrice());
        }
        
        if (cartArea != null) {
            cartArea.setText(sb.toString());
        }
        cartTotalLabel.setText("Total: $" + total);
    }

    private void processCheckout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Order order = orderService.createOrder(currentUser.getUserId(), new ArrayList<>(cart));
        if (order != null) {
            boolean paymentSuccess = orderService.processPayment(order.getOrderId(), Payment.PaymentType.WALLET);
            if (paymentSuccess) {
                // Show receipt
                new ReceiptUI(this, order).setVisible(true);
                JOptionPane.showMessageDialog(this, 
                    "Order placed successfully!\nOrder ID: " + order.getOrderId(), 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                cart.clear();
                updateCartDisplay();
                updateBalanceDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed. Please check your balance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create order. Please check your balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showOrderHistory() {
        List<Order> userOrders = orderService.getUserOrders(currentUser.getUserId());
        
        if (userOrders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No orders found", "Order History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create order history dialog
        JDialog historyDialog = new JDialog(this, "Order History", true);
        historyDialog.setSize(600, 400);
        historyDialog.setLocationRelativeTo(this);

        String[] columns = {"Order ID", "Amount", "Status", "Payment", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        JTable ordersTable = new JTable(model);
        for (Order order : userOrders) {
            model.addRow(new Object[]{
                order.getOrderId(),
                "$" + order.getTotalAmount(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getOrderTime().format(java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm"))
            });
        }

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        historyDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewReceiptBtn = new JButton("View Receipt");
        viewReceiptBtn.addActionListener(e -> {
            int selectedRow = ordersTable.getSelectedRow();
            if (selectedRow != -1) {
                int orderId = (Integer) ordersTable.getValueAt(selectedRow, 0);
                Order order = orderService.getOrderById(orderId);
                if (order != null) {
                    new ReceiptUI(this, order).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(historyDialog, "Please select an order", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> historyDialog.dispose());
        
        buttonPanel.add(viewReceiptBtn);
        buttonPanel.add(closeBtn);
        historyDialog.add(buttonPanel, BorderLayout.SOUTH);

        historyDialog.setVisible(true);
    }
}
package com.cvs.gui;

import com.cvs.dao.IngredientDAO;
import com.cvs.models.CoffeeMenu;
import com.cvs.models.Ingredient;
import com.cvs.models.Order;
import com.cvs.service.AdminService;
import com.cvs.service.ReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class AdminDashboardUI extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(139, 69, 19);
    private static final Color SECONDARY_COLOR = new Color(210, 180, 140);
    
    private final AdminService adminService;
    private final ReportService reportService;
    private final IngredientDAO ingredientDAO;
    private JTabbedPane tabbedPane;
    private JTable menuTable, ordersTable, inventoryTable;
    private JLabel statsLabel;

    public AdminDashboardUI() {
        this.adminService = new AdminService();
        this.reportService = new ReportService();
        this.ingredientDAO = new IngredientDAO();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setTitle("☕ Coffee Vending System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(SECONDARY_COLOR);

        // Header
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📊 Dashboard", createDashboardPanel());
        tabbedPane.addTab("☕ Menu Management", createMenuPanel());
        tabbedPane.addTab("📋 Orders", createOrdersPanel());
        tabbedPane.addTab("📦 Inventory", createInventoryPanel());
        tabbedPane.addTab("📈 Reports", createReportsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("🔧 Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI().setVisible(true);
        });

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(logoutBtn, BorderLayout.EAST);

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        
        statsLabel = new JLabel("<html><h3>Loading statistics...</h3></html>");
        statsPanel.add(statsLabel);
        
        panel.add(statsPanel, BorderLayout.NORTH);

        // Quick Actions
        JPanel actionsPanel = new JPanel(new FlowLayout());
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));
        
        JButton refreshBtn = new JButton("Refresh Data");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton addCoffeeBtn = new JButton("Add Coffee Item");
        addCoffeeBtn.addActionListener(e -> showAddCoffeeDialog());
        
        actionsPanel.add(refreshBtn);
        actionsPanel.add(addCoffeeBtn);
        
        panel.add(actionsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Menu Table
        String[] columns = {"ID", "Name", "Price", "Description", "Available"};
        DefaultTableModel menuModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        menuTable = new JTable(menuModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(menuTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton addBtn = new JButton("Add Item");
        addBtn.addActionListener(e -> showAddCoffeeDialog());
        
        JButton editBtn = new JButton("Edit Item");
        editBtn.addActionListener(e -> showEditCoffeeDialog());
        
        JButton toggleBtn = new JButton("Toggle Availability");
        toggleBtn.addActionListener(e -> toggleCoffeeAvailability());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(toggleBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Orders Table
        String[] columns = {"Order ID", "User ID", "Amount", "Payment Status", "Order Status", "Time"};
        DefaultTableModel ordersModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        ordersTable = new JTable(ordersModel);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton updateStatusBtn = new JButton("Update Status");
        updateStatusBtn.addActionListener(e -> showUpdateOrderStatusDialog());
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadOrdersData());
        
        buttonPanel.add(updateStatusBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadData() {
        loadMenuData();
        loadOrdersData();
        loadInventoryData();
        updateStats();
    }

    private void loadMenuData() {
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
        model.setRowCount(0);
        
        List<CoffeeMenu> menuItems = adminService.getAllCoffeeItems();
        for (CoffeeMenu item : menuItems) {
            model.addRow(new Object[]{
                item.getCoffeeId(),
                item.getName(),
                "$" + item.getPrice(),
                item.getDescription(),
                item.isAvailable() ? "Yes" : "No"
            });
        }
    }

    private void loadOrdersData() {
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.setRowCount(0);
        
        List<Order> orders = adminService.getAllOrders();
        for (Order order : orders) {
            model.addRow(new Object[]{
                order.getOrderId(),
                order.getUserId(),
                "$" + order.getTotalAmount(),
                order.getPaymentStatus(),
                order.getOrderStatus(),
                order.getOrderTime()
            });
        }
    }

    private void updateStats() {
        int totalOrders = adminService.getTotalOrders();
        BigDecimal totalRevenue = adminService.getTotalRevenue();
        int totalUsers = adminService.getAllUsers().size();
        int totalMenuItems = adminService.getAllCoffeeItems().size();

        String statsHtml = String.format(
            "<html><h3>System Statistics</h3>" +
            "<p><b>Total Orders:</b> %d</p>" +
            "<p><b>Total Revenue:</b> $%s</p>" +
            "<p><b>Total Users:</b> %d</p>" +
            "<p><b>Menu Items:</b> %d</p></html>",
            totalOrders, totalRevenue, totalUsers, totalMenuItems
        );
        
        statsLabel.setText(statsHtml);
    }

    private void showAddCoffeeDialog() {
        JDialog dialog = new JDialog(this, "Add Coffee Item", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        JTextField nameField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(nameField, gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Price:"), gbc);
        JTextField priceField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(priceField, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Description:"), gbc);
        JTextField descField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(descField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                BigDecimal price = new BigDecimal(priceField.getText().trim());
                String description = descField.getText().trim();
                
                if (adminService.addCoffeeItem(name, price, description)) {
                    JOptionPane.showMessageDialog(dialog, "Coffee item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadMenuData();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add coffee item", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Inventory Table
        String[] columns = {"ID", "Name", "Quantity", "Unit", "Min Threshold", "Status"};
        DefaultTableModel inventoryModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        inventoryTable = new JTable(inventoryModel);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton updateBtn = new JButton("Update Quantity");
        updateBtn.addActionListener(e -> showUpdateQuantityDialog());
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadInventoryData());
        
        JButton lowStockBtn = new JButton("Show Low Stock");
        lowStockBtn.addActionListener(e -> showLowStockItems());
        
        buttonPanel.add(updateBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(lowStockBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Report display area
        JTextArea reportArea = new JTextArea(20, 50);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Report Output"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Report buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Generate Reports"));
        
        JButton dailyBtn = new JButton("Daily Report");
        dailyBtn.addActionListener(e -> {
            reportArea.setText(reportService.generateDailyReport());
        });
        
        JButton weeklyBtn = new JButton("Weekly Report");
        weeklyBtn.addActionListener(e -> {
            reportArea.setText(reportService.generateWeeklyReport());
        });
        
        JButton monthlyBtn = new JButton("Monthly Report");
        monthlyBtn.addActionListener(e -> {
            reportArea.setText(reportService.generateMonthlyReport());
        });
        
        JButton userStatsBtn = new JButton("User Statistics");
        userStatsBtn.addActionListener(e -> {
            reportArea.setText(reportService.generateUserStatistics());
        });
        
        buttonPanel.add(dailyBtn);
        buttonPanel.add(weeklyBtn);
        buttonPanel.add(monthlyBtn);
        buttonPanel.add(userStatsBtn);
        
        panel.add(buttonPanel, BorderLayout.NORTH);

        return panel;
    }

    private void loadInventoryData() {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.setRowCount(0);
        
        List<Ingredient> ingredients = ingredientDAO.getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            String status = ingredient.isLowStock() ? "LOW STOCK" : "OK";
            model.addRow(new Object[]{
                ingredient.getIngredientId(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                ingredient.getMinThreshold(),
                status
            });
        }
    }

    private void showUpdateQuantityDialog() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an ingredient", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int ingredientId = (Integer) inventoryTable.getValueAt(selectedRow, 0);
        String ingredientName = (String) inventoryTable.getValueAt(selectedRow, 1);
        int currentQuantity = (Integer) inventoryTable.getValueAt(selectedRow, 2);

        String input = JOptionPane.showInputDialog(this, 
            String.format("Update quantity for %s\nCurrent: %d", ingredientName, currentQuantity),
            "Update Quantity", 
            JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                int newQuantity = Integer.parseInt(input.trim());
                if (newQuantity >= 0) {
                    if (ingredientDAO.updateIngredientQuantity(ingredientId, newQuantity)) {
                        JOptionPane.showMessageDialog(this, "Quantity updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadInventoryData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update quantity", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Quantity must be non-negative", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showLowStockItems() {
        List<Ingredient> lowStockItems = ingredientDAO.getLowStockIngredients();
        if (lowStockItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No low stock items found!", "Low Stock Alert", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder("⚠️ LOW STOCK ALERT:\n\n");
            for (Ingredient item : lowStockItems) {
                message.append(String.format("%s: %d %s (Min: %d)\n", 
                    item.getName(), item.getQuantity(), item.getUnit(), item.getMinThreshold()));
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Low Stock Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showEditCoffeeDialog() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a coffee item to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Edit functionality will be implemented in Phase 3", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
    }

    private void toggleCoffeeAvailability() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a coffee item", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int coffeeId = (Integer) menuTable.getValueAt(selectedRow, 0);
        boolean currentAvailability = "Yes".equals(menuTable.getValueAt(selectedRow, 4));
        
        if (adminService.toggleCoffeeAvailability(coffeeId, !currentAvailability)) {
            JOptionPane.showMessageDialog(this, "Availability updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadMenuData();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update availability", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showUpdateOrderStatusDialog() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int orderId = (Integer) ordersTable.getValueAt(selectedRow, 0);
        Order.OrderStatus[] statuses = Order.OrderStatus.values();
        
        Order.OrderStatus selectedStatus = (Order.OrderStatus) JOptionPane.showInputDialog(
            this, "Select new status:", "Update Order Status",
            JOptionPane.QUESTION_MESSAGE, null, statuses, statuses[0]);
        
        if (selectedStatus != null) {
            if (adminService.updateOrderStatus(orderId, selectedStatus)) {
                JOptionPane.showMessageDialog(this, "Order status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadOrdersData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update order status", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
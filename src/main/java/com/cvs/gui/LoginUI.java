package com.cvs.gui;

import com.cvs.models.User;
import com.cvs.service.AdminService;
import com.cvs.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginUI extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(139, 69, 19);
    private static final Color SECONDARY_COLOR = new Color(210, 180, 140);
    
    private final UserService userService;
    private final AdminService adminService;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JRadioButton userRadio, adminRadio;

    public LoginUI() {
        this.userService = new UserService();
        this.adminService = new AdminService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("☕ Coffee Vending System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = createMainPanel();
        add(mainPanel);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SECONDARY_COLOR);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("☕ Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Login Type
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Login as:"), gbc);

        JPanel radioPanel = new JPanel(new FlowLayout());
        radioPanel.setBackground(SECONDARY_COLOR);
        userRadio = new JRadioButton("User", true);
        adminRadio = new JRadioButton("Admin");
        ButtonGroup group = new ButtonGroup();
        group.add(userRadio);
        group.add(adminRadio);
        radioPanel.add(userRadio);
        radioPanel.add(adminRadio);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(radioPanel, gbc);

        // Email/Username
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email/Username:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(SECONDARY_COLOR);
        
        JButton loginBtn = createStyledButton("Login");
        loginBtn.addActionListener(this::handleLogin);
        
        JButton registerBtn = createStyledButton("Register");
        registerBtn.addActionListener(e -> openRegisterUI());
        
        JButton backBtn = createStyledButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            new MainUI().setVisible(true);
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private void handleLogin(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userRadio.isSelected()) {
            // User login
            User user = userService.authenticateUser(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new UserDashboardUI(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Admin login
            if (adminService.authenticateAdmin(email, password)) {
                JOptionPane.showMessageDialog(this, "Admin login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new AdminDashboardUI().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openRegisterUI() {
        dispose();
        new RegisterUI().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}
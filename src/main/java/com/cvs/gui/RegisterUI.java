package com.cvs.gui;

import com.cvs.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterUI extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(139, 69, 19);
    private static final Color SECONDARY_COLOR = new Color(210, 180, 140);
    
    private final UserService userService;
    private JTextField nameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;

    public RegisterUI() {
        this.userService = new UserService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("☕ Coffee Vending System - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
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
        JLabel titleLabel = new JLabel("📝 Register New User", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Full Name:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(nameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Confirm Password:"), gbc);
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(confirmPasswordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(SECONDARY_COLOR);
        
        JButton registerBtn = createStyledButton("Register");
        registerBtn.addActionListener(e -> handleRegister());
        
        JButton backBtn = createStyledButton("Back to Login");
        backBtn.addActionListener(e -> {
            dispose();
            new LoginUI().setVisible(true);
        });

        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        return button;
    }

    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Register user
        boolean success = userService.registerUser(name, email, password);
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Registration successful!\nWelcome bonus: $10.00 added to your account.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginUI().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Registration failed. Email may already be in use.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}